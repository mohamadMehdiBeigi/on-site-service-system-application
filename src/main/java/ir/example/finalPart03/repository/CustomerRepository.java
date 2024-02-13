package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    @Query(nativeQuery = true, value =
            "select count(c.email) " +
                    "from final_part3.users c " +
                    "where c.email =:email " +
                    "and c.id !=:customerIdForUpdate " +
                    "and c.dtype = 'Customer'")
    Integer checkUniqueEmailForExistingCustomer(String email, Long customerIdForUpdate);

    @Query(nativeQuery = true, value =
            "select count(c.email) " +
                    "from final_part3.users c " +
                    "where c.email = :email and c.dtype = 'Customer'")
    Integer checkUniqueEmailForNewCustomer(String email);
}
