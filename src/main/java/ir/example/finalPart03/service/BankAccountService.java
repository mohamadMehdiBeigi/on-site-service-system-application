package ir.example.finalPart03.service;

import ir.example.finalPart03.model.BankAccount;

public interface BankAccountService {

    Double orderPaymentByCustomerFromCredit(Long customerId, Double paymentAmount);
    BankAccount orderPaymentByCustomerByOnlinePayment(Long customerId, Double paymentAmount);

    void depositToSpecialistBalance(Long specialistId, Double depositAmount);
}
