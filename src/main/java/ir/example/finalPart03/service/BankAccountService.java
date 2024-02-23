package ir.example.finalPart03.service;

import ir.example.finalPart03.model.BankAccount;

public interface BankAccountService {


    BankAccount saveBankAccount(BankAccount bankAccount, Long specialistId, Long customerId);

    void finalPaymentByCustomerFromCredit(Long customerId, Long specialistId, Double paymentAmount, Long orderId);

    void finalPaymentByCustomerFromOnlinePaymentGateway(BankAccount bankAccount, Long specialistId, Double depositAmount, Long orderId);

    BankAccount findBankAccBySpecialistId(Long specialist);

    BankAccount findBankAccByCustomerId(Long customerId);
}
