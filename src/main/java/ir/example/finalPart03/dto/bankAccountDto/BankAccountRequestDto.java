package ir.example.finalPart03.dto.bankAccountDto;

import ir.example.finalPart03.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccountRequestDto {

    private String bankAccountNumber;

    private Integer cvv2;

    private Integer year;

    private Integer month;

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
