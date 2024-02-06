package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByCustomerId(Long customerId);
    Optional<BankAccount> findBySpecialistId(Long specialistId);
}
