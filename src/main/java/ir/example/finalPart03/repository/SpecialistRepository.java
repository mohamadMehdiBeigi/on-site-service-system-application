package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    Optional<Specialist> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Specialist a SET a.enabled=true WHERE a.email=:email")
    int enableAppUser(String email);

    @Query(nativeQuery = true, value =
            "select count(c.email) " +
                    "from final_part3.users c " +
                    "where c.email =:email " +
                    "and c.id !=:specialistIdForUpdate " +
                    "and c.dtype = 'Specialist'; ")
    Integer checkUniqueEmailForExistingSpecialist(String email, Long specialistIdForUpdate);

    @Query(nativeQuery = true, value =
            "select count(c.email) " +
                    "from final_part3.users c " +
                    "where c.email = :email and c.dtype = 'Specialist'")
    Integer checkUniqueEmailForNewSpecialist(String email);

    @Query(nativeQuery = true, value =
            "select avg(comments.score) " +
                    "from final_part3.comments  " +
                    "join final_part3.\"order\" o on comments.order_id = o.id " +
                    "join final_part3.sub_services ss on ss.id = o.sub_services_id " +
                    "join final_part3.users_sub_services uss on ss.id = uss.sub_service_id " +
                    "join final_part3.users u on u.id = uss.user_id " +
                    "where dtype = 'Specialist' " +
                    "and u.id =:specialistId")
    Double avgSpecialistScoreBySpecialistId(Long specialistId);



}

