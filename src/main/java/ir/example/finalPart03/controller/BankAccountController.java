package ir.example.finalPart03.controller;

import ir.example.finalPart03.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-getaway")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    private ModelMapper modelMapper;






//    @GetMapping("/payment/{orderId}/{price}/{specialistId}")
//    public Map<String, Object> payment(@PathVariable Long orderId, @PathVariable Double price, @PathVariable Long specialistId) {
//
//        return Map.of("orderId", orderId, "price", price, "specialistId", specialistId);
//    }


}
