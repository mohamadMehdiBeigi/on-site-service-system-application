package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Admin a SET a.enabled=true WHERE a.email=:email")
    int enableAppUser(String email);

    @Query(
            "select count(a.email) " +
                    "from Admin a " +
                    "where a.email =:email"
    )
    Integer checkUniqueEmail(String email);



}
