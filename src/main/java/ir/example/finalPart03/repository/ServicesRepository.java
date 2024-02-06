package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    @Query(nativeQuery = true, value =
            "select count(s.service_name) " +
                    "from final_part3.services s " +
                    "where s.service_name =:serviceName " +
                    "and s.id !=:serviceIdForUpdate")
    Integer checkUniqueServiceNameForExistingServices(String serviceName, Long serviceIdForUpdate);

    @Query(nativeQuery = true, value =
            "select count(s.service_name) " +
                    "from final_part3.services s " +
                    "where s.service_name = :serviceName")
    Integer checkUniqueServiceNameForNewServices(String serviceName);
}
