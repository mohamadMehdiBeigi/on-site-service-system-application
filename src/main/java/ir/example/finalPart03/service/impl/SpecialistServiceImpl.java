package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.DuplicateException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.dto.registerations.RegistrationRequest;
import ir.example.finalPart03.model.BankAccount;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.model.Suggestions;
import ir.example.finalPart03.model.enums.Role;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.BankAccountRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SubServicesRepository;
import ir.example.finalPart03.repository.SuggestionsRepository;
import ir.example.finalPart03.service.SpecialistService;
import ir.example.finalPart03.util.ImageReader;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;

    private final SubServicesRepository subServicesRepository;

    private final BankAccountRepository bankAccountRepository;

    private final SuggestionsRepository suggestionsRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RegistrationService registrationService;

    private final ModelMapper modelMapper;

    @Override
    public Optional<Specialist> findByEmail(String email) {
        return specialistRepository.findByEmail(email);
    }


    @Transactional
    @Override
    public Specialist saveSpecialist(Specialist specialist, MultipartFile file) {
        if (!checkUniqueEmail(specialist.getEmail(), specialist.getId())) {
            throw new DuplicateException("this Email is already exists");
        }
        specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
        specialist.setSpecialistStatus(SpecialistStatus.WAIT_FOR_CONFIRMED);
        try {
            specialist.setImage(ImageReader.uploadProfilePicture(file, specialist.getFirstname(), specialist.getLastname(), specialist.getEmail()));

        } catch (IOException e) {
            throw new BadRequestException("this file you selected is not supported");
        }
        Specialist savedSpecialist;
        try {
            savedSpecialist = specialistRepository.save(specialist);
        } catch (Exception e) {
            throw new BadRequestException("Can't save or update customer data: " + e.getMessage());
        }
        try {
            RegistrationRequest registrationRequest = modelMapper.map(specialist, RegistrationRequest.class);
            registrationService.register(registrationRequest, Role.ROLE_SPECIALIST);
            return savedSpecialist;

        } catch (Exception e) {
            throw new NotFoundException("there is problem with email verification");
        }
    }

    @Override
    public Boolean checkingSpecialistStatus(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("Specialist with ID: " + specialistId + " was not found."));

        SpecialistStatus status = specialist.getSpecialistStatus();
        return status == SpecialistStatus.CONFIRMED;
    }

    @Transactional
    @Override
    public void confirmingSpecialStatus(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("cant find anything whit this id."));
        if (checkingSpecialistStatus(specialistId)) {
            throw new BadRequestException("this account is already CONFIRMED");
        }
        specialist.setSpecialistStatus(SpecialistStatus.CONFIRMED);
        try {
            specialistRepository.save(specialist);
        } catch (Exception e) {
            throw new BadRequestException("there is problem with updating specialistStatus" + e.getMessage());
        }
    }

    @Override
    public void saveImageToFile(Long id) {
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("this id is not found!"));
        byte[] image = specialist.getImage();
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Data\\IdeaProjects\\finalPart3\\src\\main\\resources\\extractedImage\\id_number" + id + ".jpg")) {
            fos.write(image);
        } catch (IOException e) {
            throw new BadRequestException("img is not found" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Specialist changePassword(Long id, String oldPassword, String password, String confirmingPassword) {
        if (!Objects.equals(password, confirmingPassword)) {
            throw new RuntimeException("password and confirming password is not the same");
        }
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("this specialistId is not found!"));

        if (!Objects.equals(oldPassword, specialist.getPassword())) {
            throw new BadRequestException("your oldPassword is incorrect, please enter your valid old password \n");
        }
        String encode = passwordEncoder.encode(password);
        specialist.setPassword(encode);
        return specialistRepository.save(specialist);
    }


    @Override
    public Boolean checkUniqueEmail(String email, Long specialistIdForUpdate) {
        if (specialistIdForUpdate == null) {
            Integer checked = specialistRepository.checkUniqueEmailForNewSpecialist(email);
            if (checked > 0) {
                throw new DuplicateException("The email you entered is already exist. Try another.");
            }
        } else {
            Integer checked = specialistRepository.checkUniqueEmailForExistingSpecialist(email, specialistIdForUpdate);
            if (checked > 0) {
                throw new DuplicateException("The email you entered is already used by another specialist.");
            }
        }
        return true;
    }

    @Transactional
    @Override
    public void addSubServiceToSpecialist(Long specialistId, Long subServiceId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("Specialist not found"));
        if (specialist.getSpecialistStatus() == SpecialistStatus.NEW || specialist.getSpecialistStatus() == SpecialistStatus.WAIT_FOR_CONFIRMED){
            throw new BadRequestException("this specialist status is new or waiting for confirmed, first it must've confirmed by admin");
        }
        SubServices subService = subServicesRepository
                .findById(subServiceId).orElseThrow(() -> new NotFoundException("SubService not found"));

        if (specialist.getSubServices() == null) {
            specialist.setSubServices(new HashSet<>());
        }

        if (subService.getSpecialists() == null) {
            subService.setSpecialists(new HashSet<>());
        }
        subService.getSpecialists().add(specialist);
        specialist.getSubServices().add(subService);
        try {
            specialistRepository.save(specialist);
        } catch (Exception e) {
            throw new BadRequestException("invalid id input for make connection between specialist & subService" + e.getMessage());
        }
    }

    @Override
    public Specialist seeAverageScore(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("cant find specialist,wrong specialistId"));
        Double averageScore;
        try {
            averageScore = specialistRepository.avgSpecialistScoreBySpecialistId(specialistId);
        } catch (Exception e) {
            throw new BadRequestException("invalid specialistId for get average score.\n" + e.getMessage());
        }
        specialist.setAverageScores(averageScore);
        try {
            return specialistRepository.save(specialist);

        } catch (Exception e) {
            throw new BadRequestException("cant set average score to specialist,try again" + e.getMessage());
        }

    }

    @Override
    public Specialist findById(Long specialistId) {
        return specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NotFoundException("cant find specialist,wrong specialistId"));

    }

    @Override
    public void deleteSpecialist(Long specialistId) {

        List<SubServices> allSubServiceBySpecialistsId;
        try {
            allSubServiceBySpecialistsId = subServicesRepository.findAllBySpecialistsId(specialistId);
        } catch (Exception e) {
            throw new BadRequestException("there is no SubService data with this ID.   " + e.getMessage());
        }
        for (SubServices subServices : allSubServiceBySpecialistsId) {
            subServices.setSpecialists(null);
            subServicesRepository.save(subServices);
        }
        BankAccount bankAccount = null;
        bankAccount = bankAccountRepository.findBySpecialistId(specialistId).orElse(bankAccount);
        if (bankAccount != null) {
            try {
                bankAccountRepository.delete(bankAccount);
            } catch (Exception e) {
                throw new BadRequestException("cant delete BankAccount" + e.getMessage());
            }
        }

        List<Suggestions> allSuggestionBySpecialistId = suggestionsRepository.findAllBySpecialistId(specialistId);

        for (Suggestions suggestions : allSuggestionBySpecialistId) {
            suggestions.setSpecialist(null);
            suggestionsRepository.save(suggestions);
        }
        try {
            specialistRepository.deleteById(specialistId);

        } catch (Exception e) {
            throw new BadRequestException("there is problem with deleting this specialist  . " + e.getMessage());
        }

    }
}
