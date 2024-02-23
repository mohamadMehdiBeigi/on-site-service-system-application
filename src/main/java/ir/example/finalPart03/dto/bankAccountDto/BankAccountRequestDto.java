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
@Getter
@Setter
public class BankAccountRequestDto {

    @Pattern(regexp = "^(589463|627353|628023)\\d{10}$", message = "either this bank is not exist nor you must enter 16 number")
    private String bankAccountNumber;

    private Integer cvv2;

    private Integer year;

    private Integer month;

    @Pattern(regexp = "^\\S+$", message = "password cant have space.")
    private String password;

    private Long orderId;

    private Double price;

    private Long specialistId;


    public static BankAccount dtoToBankAccount(BankAccountRequestDto bankAccountRequestDto) {

        return new BankAccount(
                bankAccountRequestDto.getBankAccountNumber(),
                bankAccountRequestDto.cvv2,
                LocalDate.of(bankAccountRequestDto.year, bankAccountRequestDto.month, 1),
                bankAccountRequestDto.password
        );
    }

}
