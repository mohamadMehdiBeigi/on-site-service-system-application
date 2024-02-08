package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.bankAccountDto.BankAccountRequestDto;
import ir.example.finalPart03.model.BankAccount;
import ir.example.finalPart03.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-getaway")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

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
    public ResponseEntity<BankAccount> saveBankAccount(@RequestBody BankAccount bankAccount) {
        return new ResponseEntity<>(bankAccountService.saveBankAccount(bankAccount), HttpStatus.CREATED);
    }

//    @GetMapping("/payment/{orderId}/{price}/{specialistId}")
//    public Map<String, Object> payment(@PathVariable Long orderId, @PathVariable Double price, @PathVariable Long specialistId) {
//
//        return Map.of("orderId", orderId, "price", price, "specialistId", specialistId);
//    }


}
