package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SubServicesRepository;
import ir.example.finalPart03.service.SpecialistService;
import jakarta.persistence.NoResultException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class SpecialistServiceImpl implements SpecialistService {

    private SpecialistRepository specialistRepository;

    private SubServicesRepository subServicesRepository;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository, SubServicesRepository subServicesRepository) {
        this.specialistRepository = specialistRepository;
        this.subServicesRepository = subServicesRepository;
    }

    @Transactional
    @Override
    public Specialist saveSpecialist(Specialist specialist) {
        try {
            if (!checkUniqueEmail(specialist.getEmail(), specialist.getId())) {
                throw new ValidationException("this Email is already exists");
            }

            return specialistRepository.save(specialist);
        } catch (Exception e) {
            System.err.println("Can't save or update customer data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Specialist findByEmailAndPassword(String email, String password) {
        return specialistRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new NoResultException("this email is not found!"));

    }

    @Override
    public Boolean checkingSpecialistStatus(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NoSuchElementException("Specialist with ID: " + specialistId + " was not found."));

        SpecialistStatus status = specialist.getSpecialistStatus();
        return status == SpecialistStatus.CONFIRMED;
    }

    @Transactional
    @Override
    public void confirmingSpecialStatus(Long specialistId) {
        try {
            Specialist specialist = specialistRepository.findById(specialistId)
                    .orElseThrow(() -> new NoResultException("cant find anything whit this id."));
            specialist.setSpecialistStatus(SpecialistStatus.CONFIRMED);
            specialistRepository.save(specialist);
        } catch (Exception e) {
            System.err.println("there is problem with updating specialistStatus");
        }
    }

    @Override
    public void saveImageToFile(Long id) {
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("this id is not found!"));
        byte[] image = specialist.getImage();
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Data\\IdeaProjects\\finalPart2\\src\\main\\resources\\extractedImage\\id_number" + id + ".jpg")) {
            fos.write(image);
        } catch (IOException e) {
            System.err.println("img is not found");
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public Specialist changePassword(Long id, String password, String confirmingPassword) {
        if (!Objects.equals(password, confirmingPassword)) {
            throw new RuntimeException("password and confirming password is not the same");
        }
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new NoResultException("this specialistId is not found!"));
        specialist.setPassword(password);
        return specialistRepository.save(specialist);
    }


    @Override
    public Boolean checkUniqueEmail(String email, Long specialistIdForUpdate) {
        if (specialistIdForUpdate == null) {
            Integer checked = specialistRepository.checkUniqueEmailForNewSpecialist(email);
            if (checked > 0) {
                throw new RuntimeException("The email you entered is already exist. Try another.");
            }
        } else {
            Integer checked = specialistRepository.checkUniqueEmailForExistingSpecialist(email, specialistIdForUpdate);
            if (checked > 0) {
                throw new RuntimeException("The email you entered is already used by another specialist.");
            }
        }
        return true;
    }

    @Transactional
    @Override
    public void addSubServiceToSpecialist(Long specialistId, Long subServiceId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new RuntimeException("Specialist not found"));
        SubServices subService = subServicesRepository
                .findById(subServiceId).orElseThrow(() -> new RuntimeException("SubService not found"));

        if (specialist.getSubServices() == null) {
            specialist.setSubServices(new HashSet<>());
        }

        if (subService.getSpecialists() == null) {
            subService.setSpecialists(new HashSet<>());
        }
        subService.getSpecialists().add(specialist);
        specialist.getSubServices().add(subService);

        specialistRepository.save(specialist);
    }

    @Override
    public Specialist seeAverageScore(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new NoResultException("cant find specialist,wrong specialistId"));
        try {
            Double averageScore = specialistRepository.avgSpecialistScoreBySpecialistId(specialistId);
            specialist.setAverageScores(averageScore);
            return specialistRepository.save(specialist);

        } catch (Exception e) {
            System.err.println("cant set average score to specialist,try again");
        }
        return null;
    }
}
