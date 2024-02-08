package ir.example.finalPart03.dto.bankAccountDto;

import ir.example.finalPart03.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountResponseDto {

    private String bankAccountNumber;

    private Integer cvv2;

    private LocalDate expiryDate;

    private String password;

    public static BankAccountResponseDto bankAccountToResponseDto(BankAccount bankAccount){
        return new BankAccountResponseDto(
                bankAccount.getBankAccountNumber(),
                bankAccount.getCvv2(),
                bankAccount.getExpiryDate(),
                bankAccount.getPassword()
        );
    }
}
