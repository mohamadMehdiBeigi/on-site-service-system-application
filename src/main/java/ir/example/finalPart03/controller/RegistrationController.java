package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.customerDto.CustomerRequestDto;
import ir.example.finalPart03.dto.customerDto.CustomerResponseDto;
import ir.example.finalPart03.dto.specialistDto.SpecialistResponseDto;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.enums.Role;
import ir.example.finalPart03.service.BankAccountService;
import ir.example.finalPart03.service.CustomerService;
import ir.example.finalPart03.service.SpecialistService;
import ir.example.finalPart03.service.impl.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final SpecialistService specialistService;

    private final ModelMapper modelMapper;

    private final RegistrationService registrationService;

    private final CustomerService customerService;

    private final BankAccountService bankAccountService;


//    @PostMapping
//    public String register(@RequestBody RegistrationRequest request) {
//        return registrationService.register(request,request.getRole());
//        //return "registered";
//    }

    @PostMapping("/specialist/save")
    public ResponseEntity<SpecialistResponseDto> saveSpecialist(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String role,
            @RequestParam String password,
            @RequestParam MultipartFile file) {
        Specialist specialist = new Specialist();
        specialist.setFirstname(firstname);
        specialist.setLastname(lastname);
        specialist.setEmail(email);
        specialist.setPassword(password);
        Role byTitle = Role.findByTitle(role);
        specialist.setRole(byTitle);
        Specialist saveSpecialist = specialistService.saveSpecialist(specialist, file);
        SpecialistResponseDto specialistResponseDto = modelMapper.map(saveSpecialist, SpecialistResponseDto.class);
        return new ResponseEntity<>(specialistResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/customer/save")
    ResponseEntity<CustomerResponseDto> saveCustomer( @RequestBody CustomerRequestDto customerRequestDto) {
        Customer customer = modelMapper.map(customerRequestDto, Customer.class);
        Customer savedCustomer = customerService.saveCustomer(customer);
        CustomerResponseDto customerResponseDto = modelMapper.map(savedCustomer, CustomerResponseDto.class);

        return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
