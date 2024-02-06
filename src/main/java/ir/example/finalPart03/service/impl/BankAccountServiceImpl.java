package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.BankAccount;
import ir.example.finalPart03.repository.BankAccountRepository;
import ir.example.finalPart03.service.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Double orderPaymentByCustomerFromCredit(Long customerId, Double paymentAmount) {
        BankAccount bankAccount = bankAccountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("this customer id is not exist"));
        try {
            double remainingCredit = bankAccount.getBalance() - paymentAmount;

            if (remainingCredit < 0) {
                throw new BadRequestException("you dont have enough money on your credit, please charge your account or select <OnlinePayment>");
            }
            bankAccount.setBalance(remainingCredit);
            bankAccountRepository.save(bankAccount);
            return remainingCredit;

        } catch (RuntimeException e) {
            throw new BadRequestException("there is problem with payment");
        }

    }

    @Override
    public BankAccount orderPaymentByCustomerByOnlinePayment(Long customerId, Double paymentAmount) {
        return null;
    }


    @Override
    public void depositToSpecialistBalance(Long specialistId, Double depositAmount) {

        BankAccount bankAccount = bankAccountRepository.findBySpecialistId(specialistId)
                .orElseThrow(() -> new NotFoundException("this specialist id is not exist"));
        try {
            Double deductedDepositAmount = depositAmount * 0.7;
            double finalBalance = bankAccount.getBalance() + deductedDepositAmount;
            bankAccount.setBalance(finalBalance);
            bankAccountRepository.save(bankAccount);

        } catch (RuntimeException e) {
            throw new BadRequestException("there is problem with payment");
        }
    }
}