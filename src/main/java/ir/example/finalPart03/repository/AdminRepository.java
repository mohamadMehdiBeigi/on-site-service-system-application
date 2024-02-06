package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailAndPassword(String email, String password);

    @Query(
            "select count(a.email) " +
                    "from Admin a " +
                    "where a.email =:email"
    )
    Integer checkUniqueEmail(String email);

}
