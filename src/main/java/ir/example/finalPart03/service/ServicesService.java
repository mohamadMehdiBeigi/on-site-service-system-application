package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Services;

public interface ServicesService {

    Services saveService(Services services);

    Boolean checkUniqueServiceName(String serviceName, Long serviceIdForUpdate );

    void deleteServiceById(Long serviceId);
}
