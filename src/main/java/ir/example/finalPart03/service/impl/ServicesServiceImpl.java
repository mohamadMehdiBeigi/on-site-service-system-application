package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Services;
import ir.example.finalPart03.repository.ServicesRepository;
import ir.example.finalPart03.service.ServicesService;
import ir.example.finalPart03.service.SubServiceService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ServicesServiceImpl implements ServicesService {

    private ServicesRepository servicesRepository;

    private SubServiceService subServiceService;

    public ServicesServiceImpl(ServicesRepository servicesRepository, SubServiceService subServiceService) {
        this.servicesRepository = servicesRepository;
        this.subServiceService = subServiceService;
    }

    @Transactional
    @Override
    public Services saveService(Services services) {
        try {
            if (!checkUniqueServiceName(services.getServiceName(), services.getId())) {
                throw new ValidationException("Email already exists");
            }

            return servicesRepository.save(services);
        } catch (Exception e) {
            System.err.println("Can't save or update customer data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean checkUniqueServiceName(String serviceName, Long serviceIdForUpdate) {
        if (serviceIdForUpdate == null) {
            Integer checked = servicesRepository.checkUniqueServiceNameForNewServices(serviceName);
            if (checked > 0) {
                throw new RuntimeException("The serviceName you entered is already exist. Try another serviceName.");
            }
        } else {
            Integer checked = servicesRepository.checkUniqueServiceNameForExistingServices(serviceName, serviceIdForUpdate);
            if (checked > 0) {
                throw new RuntimeException("The serviceName you entered is already used");
            }
        }
        return true;
    }

    @Override
    public void deleteServiceById(Long serviceId) {
        try {
            subServiceService.removeSubServiceByServiceId(serviceId);
            servicesRepository.deleteById(serviceId);
        } catch (Exception e){
            throw new RuntimeException("cant delete service data,try again.");
        }
    }
}
