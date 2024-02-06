package ir.example.finalPart03.service;


import ir.example.finalPart03.model.SubServices;

import java.util.List;

public interface SubServiceService {

    SubServices saveSubService(SubServices subService);

    SubServices updateBasePriceAndDescription(Long id, Double newBasePrice, String description);


    List<SubServices> findAllByServiceId(Long servicesId);

    Boolean checkUniqueSubServiceName(String subServiceName, Long subServiceIdForUpdate );

    void deleteSubServiceById(Long subServiceId);

    void removeSubServiceByServiceId(Long serviceId);

    void deleteSubServicesOfSpecialist(Long specialistId);


}
