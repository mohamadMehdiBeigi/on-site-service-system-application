package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.DuplicateException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.Services;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SubServicesRepository;
import ir.example.finalPart03.service.SubServiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class SubServicesServiceImpl implements SubServiceService {

    private final SubServicesRepository subServicesRepository;

    private final SpecialistRepository specialistRepository;

    private final OrderRepository orderRepository;


    @Override
    public SubServices saveSubService(SubServices subService, Long serviceId) {
        if (!checkUniqueSubServiceName(subService.getSubServiceName(), subService.getId())) {
            throw new DuplicateException("Email already exists");
        }
        if (subService.getServices() == null) {
            Services services = new Services();
            services.setId(serviceId);
            subService.setServices(services);
        }
        try {
            return subServicesRepository.save(subService);
        } catch (Exception e) {
            throw new BadRequestException("cant save subService, invalid body input \n" + e.getMessage());
        }
    }

    @Override
    public SubServices updateBasePriceAndDescription(Long id, Double newBasePrice, String description) {
        SubServices subServices = subServicesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("this subService id is not found!"));
        subServices.setBasePrice(newBasePrice);
        subServices.setDescription(description);
        try {
            return subServicesRepository.save(subServices);
        } catch (Exception e) {
            throw new BadRequestException("cant update basePrice and description, invalid inputs \n" + e.getMessage());
        }
    }

    @Override
    public List<SubServices> findAllByServiceId(Long serviceId) {
        try {
            return subServicesRepository.findAllByServicesId(serviceId);

        } catch (Exception e) {
            throw new NotFoundException("this Service id is not existed \n" + e.getMessage());
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
    public List<SubServices> findAllSubService() {
        return subServicesRepository.findAll();
    }

    @Override
    public void deleteSubService(Long subServiceId) {

        SubServices subService = subServicesRepository.findById(subServiceId)
                .orElseThrow(() -> new EntityNotFoundException("SubService not found with id: " + subServiceId));

        Set<Specialist> specialists = subService.getSpecialists();

        for (Specialist specialist : specialists) {
            specialist.getSubServices().remove(subService);
            specialistRepository.save(specialist);
        }

        List<Order> allOrdersBySubServicesId = orderRepository.findAllBySubServicesId(subServiceId);
        for (Order order : allOrdersBySubServicesId) {
            order.setSubServices(null);
            orderRepository.save(order);
        }

        subServicesRepository.delete(subService);
    }

    @Override
    public void removeSubServiceByServiceId(Long serviceId) {
        try {
            List<SubServices> allByServicesId = subServicesRepository.findAllByServicesId(serviceId);
            if (allByServicesId.size() != 0) {
                subServicesRepository.removeSubServiceRelationalByServiceId(serviceId);
            }
        } catch (Exception e) {
            throw new RuntimeException("invalid serviceId, try with valid id" + e.getMessage());
        }
    }

    @Override
    public void deleteSubServicesOfSpecialist(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));

        Set<SubServices> subServices = specialist.getSubServices();

        subServices.forEach(subService -> {
            subService.getSpecialists().remove(specialist);
            subServicesRepository.delete(subService);
        });
        try {
            specialistRepository.save(specialist);

        } catch (Exception e) {
            throw new BadRequestException("cant delete this subService \n" + e.getMessage());
        }
    }


}
