package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.SubServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubServicesRepository extends JpaRepository<SubServices, Long> {

    List<SubServices> findAllByServicesId(Long servicesId);

    @Query(nativeQuery = true, value =
            "select count(s.sub_service_name) " +
                    "from final_part3.sub_services s " +
                    "where s.sub_service_name =:subServiceName " +
                    "and s.id !=:subServiceIdForUpdate")
    Integer checkUniqueSubServiceNameForExistingSubServices(String subServiceName, Long subServiceIdForUpdate);

    @Query(nativeQuery = true, value =
            "select count(s.sub_service_name) " +
                    "from final_part3.sub_services s " +
                    "where s.sub_service_name =:subServiceName")
    Integer checkUniqueSubServiceNameForNewSubServices(String subServiceName);

    @Transactional
    @Modifying
    @Query("delete from SubServices s where s.services.id = :serviceId")
    void removeSubServiceRelationalByServiceId(Long serviceId);


    List<SubServices> findAllBySpecialistsId(Long specialistId);
}
