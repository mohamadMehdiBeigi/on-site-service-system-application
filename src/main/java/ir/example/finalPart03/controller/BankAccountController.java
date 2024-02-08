package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.bankAccountDto.BankAccountRequestDto;
import ir.example.finalPart03.dto.bankAccountDto.BankAccountResponseDto;
import ir.example.finalPart03.dto.bankAccountDto.BankAccountSavingDto;
import ir.example.finalPart03.model.BankAccount;
import ir.example.finalPart03.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-getaway")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    private ModelMapper modelMapper;


    @PutMapping("/credit/{customerId}/{specialistId}/{paymentAmount}")
    public ResponseEntity<Void> orderPaymentByCustomerFromCredit(@PathVariable Long customerId, @PathVariable Long specialistId, @PathVariable Double paymentAmount) {
        bankAccountService.finalPaymentByCustomerFromCredit(customerId, specialistId, paymentAmount);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/online/finalPaymentFromOnlineGateway")
    public void finalPaymentFromOnlineGateway(@RequestBody BankAccountRequestDto bankAccountRequestDto) {
        BankAccount bankAccount = BankAccountRequestDto.dtoToBankAccount(bankAccountRequestDto);
        bankAccountService.finalPaymentByCustomerFromOnlinePaymentGateway(bankAccount, bankAccountRequestDto.getSpecialistId(), bankAccountRequestDto.getPrice());

    }

    @PostMapping("/save")
    public ResponseEntity<BankAccountResponseDto> saveBankAccount(@RequestBody BankAccountSavingDto bankAccountSavingDto) {
        BankAccount bankAccount = BankAccountSavingDto.dtoToBankAccount(bankAccountSavingDto);
        BankAccount saveBankAccount = bankAccountService.saveBankAccount(bankAccount, bankAccountSavingDto.getSpecialistId(), bankAccountSavingDto.getCustomerId());
        BankAccountResponseDto bankAccountResponseDto = modelMapper.map(saveBankAccount, BankAccountResponseDto.class);
        return new ResponseEntity<>(bankAccountResponseDto, HttpStatus.CREATED);
    }

//    @GetMapping("/payment/{orderId}/{price}/{specialistId}")
//    public Map<String, Object> payment(@PathVariable Long orderId, @PathVariable Double price, @PathVariable Long specialistId) {
//
//        return Map.of("orderId", orderId, "price", price, "specialistId", specialistId);
//    }


}
