package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface
BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query(" from BankAccount b where b.customer.id =:customerId")
    Optional<BankAccount> findByCustomerId(Long customerId);

    @Query(" from  BankAccount b where b.specialist.id = :specialistId")
    Optional<BankAccount> findBySpecialistId(Long specialistId);

    Optional<BankAccount> findByBankAccountNumberAndCvv2AndExpiryDateAndPassword(String bankAccountNumber, Integer cvv2, LocalDate expiryDate, String password);

}
