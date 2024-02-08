package ir.example.finalPart03.dto.bankAccountDto;

import ir.example.finalPart03.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BankAccountSavingDto {

    @Pattern(regexp = "^(589463|627353|628023)\\d{10}$", message = "either this bank is not exist nor you must enter 16 number")
    private String bankAccountNumber;

    private Integer cvv2;

    private LocalDate expiryDate;

    @Pattern(regexp = "^\\S+$", message = "password cant have space.")
    private String password;

    private Long specialistId;

    private Long customerId;

    public static BankAccount dtoToBankAccount(BankAccountSavingDto bankAccountSavingDto){

        return new BankAccount(
                bankAccountSavingDto.getBankAccountNumber(),
                bankAccountSavingDto.getCvv2(),
                bankAccountSavingDto.getExpiryDate(),
                bankAccountSavingDto.getPassword()
        );

    }
}
