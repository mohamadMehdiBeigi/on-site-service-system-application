package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.DuplicateException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SubServicesRepository;
import ir.example.finalPart03.service.SubServiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SubServicesServiceImpl implements SubServiceService {

    private SubServicesRepository subServicesRepository;

    private SpecialistRepository specialistRepository;


    public SubServicesServiceImpl(SubServicesRepository subServicesRepository, SpecialistRepository specialistRepository) {
        this.subServicesRepository = subServicesRepository;
        this.specialistRepository = specialistRepository;
    }

    @Transactional
    @Override
    public SubServices saveSubService(SubServices subService) {
        try {
            if (!checkUniqueSubServiceName(subService.getSubServiceName(), subService.getId())) {
                throw new DuplicateException("Email already exists");
            }

            return subServicesRepository.save(subService);
        } catch (Exception e) {
            throw new BadRequestException("cant save subService, invalid body input" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public SubServices updateBasePriceAndDescription(Long id, Double newBasePrice, String description) {
        SubServices subServices = subServicesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("this subService id is not found!"));
        try {
            subServices.setBasePrice(newBasePrice);
            subServices.setDescription(description);
            return subServicesRepository.save(subServices);
        } catch (Exception e) {
            throw new BadRequestException("cant update basePrice and description, invalid inputs" + e.getMessage());
        }
    }

    @Override
    public List<SubServices> findAllByServiceId(Long serviceId) {
        try {
            return subServicesRepository.findAllByServicesId(serviceId);

        } catch (Exception e) {
            throw new NotFoundException("this Service id is not existed" + e.getMessage());
        }
    }

    @Override
    public Boolean checkUniqueSubServiceName(String subServiceName, Long subServiceIdForUpdate) {
        if (subServiceIdForUpdate == null) {
            Integer checked = subServicesRepository.checkUniqueSubServiceNameForNewSubServices(subServiceName);
            if (checked > 0) {
                throw new DuplicateException("The email you entered is already exist. Try another email.");
            }
        } else {
            Integer checked = subServicesRepository.checkUniqueSubServiceNameForExistingSubServices(subServiceName, subServiceIdForUpdate);
            if (checked > 0) {
                throw new DuplicateException("The email you entered is already used by another customer.");
            }
        }
        return true;
    }

    @Override
    public void deleteSubServiceById(Long subServiceId) {
        try {
            subServicesRepository.deleteById(subServiceId);
        } catch (Exception e) {
            throw new RuntimeException("cant delete subService,please enter valid id" + e.getMessage());
        }
    }

    @Override
    public void removeSubServiceByServiceId(Long serviceId) {
        try {
            subServicesRepository.removeSubServiceRelationalByServiceId(serviceId);
        } catch (Exception e) {
            throw new RuntimeException("invalid serviceId, try with valid id" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteSubServicesOfSpecialist(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));

        Set<SubServices> subServices = specialist.getSubServices();

        subServices.forEach(subService -> {
            subService.getSpecialists().remove(specialist);
            subServicesRepository.delete(subService);
        });

        specialistRepository.save(specialist);
    }


}
