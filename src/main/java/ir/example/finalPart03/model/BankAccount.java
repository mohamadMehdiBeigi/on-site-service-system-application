package ir.example.finalPart03.model;

import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class BankAccount extends BaseEntity<Long> implements Serializable {

    @Column(name = "bank_account_number", nullable = false)
    @Pattern(regexp = "^(589463|627353|628023)\\d{10}$", message = "either this bank is not exist nor you must enter 16 number")
    String bankAccountNumber;

    @Column(name = "CVV2", nullable = false)
    Integer cvv2;

    @Column(name = "expiry_date", nullable = false)
    LocalDate expiryDate;

    @Min(value = 0, message = "balance cant be lower than zero")
    Double balance;

    @ManyToOne
    Specialist specialist;

    @ManyToOne
    Customer customer;


}
