package ir.example.finalPart03.model;

import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class BankAccount extends BaseEntity<Long> implements Serializable {

    @Column(name = "bank_account_number", nullable = false, unique = true)
    @Pattern(regexp = "^(589463|627353|628023)\\d{10}$", message = "either this bank is not exist nor you must enter 16 number")
    String bankAccountNumber;

    @Column(name = "CVV2", nullable = false)
    Integer cvv2;

    @Column(name = "expiry_date", nullable = false)
    LocalDate expiryDate;

    @Min(value = 0, message = "balance cant be lower than zero")
    Double balance;

    @Pattern(regexp = "^\\S+$", message = "password cant have space.")
    String password;

    @ManyToOne
    Specialist specialist;

    @ManyToOne
    Customer customer;

    public BankAccount(String bankAccountNumber, Integer cvv2, LocalDate expiryDate, String password) {
        this.bankAccountNumber = bankAccountNumber;
        this.cvv2 = cvv2;
        this.expiryDate = expiryDate;
        this.password = password;
    }

    public BankAccount() {
    }

    public BankAccount(Long aLong, String bankAccountNumber, Integer cvv2, LocalDate expiryDate, Double balance, String password, Specialist specialist, Customer customer) {
        super(aLong);
        this.bankAccountNumber = bankAccountNumber;
        this.cvv2 = cvv2;
        this.expiryDate = expiryDate;
        this.balance = balance;
        this.password = password;
        this.specialist = specialist;
        this.customer = customer;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Integer getCvv2() {
        return cvv2;
    }

    public void setCvv2(Integer cvv2) {
        this.cvv2 = cvv2;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PrePersist
    private void init(){
        this.balance = 50.0;
    }


}
