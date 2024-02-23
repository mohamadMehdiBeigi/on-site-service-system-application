package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.DuplicateException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.BankAccount;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.repository.BankAccountRepository;
import ir.example.finalPart03.service.BankAccountService;
import ir.example.finalPart03.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final OrderService orderService;


    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount, Long specialistId, Long customerId) {

        if (specialistId != null && customerId != null) {
            throw new DuplicateException("account number cant not be belong to a specialist and customer at the same time");
        }
        if (specialistId != null) {
            Specialist specialist = new Specialist();
            specialist.setId(specialistId);
            bankAccount.setSpecialist(specialist);

        } else if (customerId != null) {
            Customer customer = new Customer();
            customer.setId(customerId);
            bankAccount.setCustomer(customer);
        }
        try {
            return bankAccountRepository.save(bankAccount);

        } catch (Exception e) {
            throw new BadRequestException("invalid input for saving your bank account" + e.getMessage());
        }
    }

    @Override
    public void finalPaymentByCustomerFromCredit(Long customerId, Long specialistId, Double paymentAmount, Long orderId) {
        Order order = orderService.findById(orderId);
        if (order.getOrderStatus() == OrderStatus.PAID){
            throw new BadRequestException("this order is already PAID");
        }
        Double payment = orderPaymentByCustomerFromCredit(customerId, paymentAmount);
        depositToSpecialistBalance(specialistId, payment);
        orderService.changeOrderStatusToPaid(orderId);
    }

    @Override
    public void finalPaymentByCustomerFromOnlinePaymentGateway(BankAccount bankAccount, Long specialistId, Double depositAmount, Long orderId) {

        BankAccount byAllColumns = findByAllColumns(bankAccount);
        if (byAllColumns == null) {
            throw new NotFoundException("there is no bank account with this inputs");
        }
        depositToSpecialistBalance(specialistId, depositAmount);
        try {
            orderService.changeOrderStatusToPaid(orderId);

        } catch (Exception e) {
            throw new BadRequestException("invalid inputs for deposit to specialist account" + e.getMessage());
        }
    }


    private Double orderPaymentByCustomerFromCredit(Long customerId, Double paymentAmount) {
        BankAccount bankAccount = bankAccountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("this customer id is not exist"));

        double bankAccountBalance = bankAccount.getBalance();

        if (bankAccountBalance < paymentAmount) {
            throw new BadRequestException("you dont have enough money on your credit, please charge your account or select <OnlinePayment>");
        }
        double remainingCredit = bankAccountBalance - paymentAmount;
        bankAccount.setBalance(remainingCredit);
        try {
            bankAccountRepository.save(bankAccount);
            return remainingCredit;

        } catch (RuntimeException e) {
            throw new BadRequestException("there is problem with payment");
        }

    }


    private void depositToSpecialistBalance(Long specialistId, Double depositAmount) {

        BankAccount bankAccount = bankAccountRepository.findBySpecialistId(specialistId)
                .orElseThrow(() -> new NotFoundException("this specialist id is not exist"));

        Double deductedDepositAmount = depositAmount * 0.7;
        double finalBalance = bankAccount.getBalance() + deductedDepositAmount;
        bankAccount.setBalance(finalBalance);

        try {
            bankAccountRepository.save(bankAccount);

        } catch (RuntimeException e) {
            throw new BadRequestException("there is problem with payment");
        }
    }


    private BankAccount findByAllColumns(BankAccount bankAccount) {
        return bankAccountRepository.findByBankAccountNumberAndCvv2AndExpiryDateAndPassword(bankAccount.getBankAccountNumber(), bankAccount.getCvv2(), bankAccount.getExpiryDate(), bankAccount.getPassword())
                .orElseThrow(() -> new NotFoundException("there is no bank account with this inputs"));
    }

    @Override
    public BankAccount findBankAccBySpecialistId(Long specialist) {
        return bankAccountRepository.findBySpecialistId(specialist)
                .orElseThrow(() -> new NotFoundException("there is no bank account by specialist id"));
    }

    @Override
    public BankAccount findBankAccByCustomerId(Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("there is no bank account by customer id"));
    }

}