package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.customerDto.CustomerRequestDto;
import ir.example.finalPart03.dto.customerDto.CustomerResponseDto;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    private ModelMapper modelMapper;

    @PostMapping("/customer/save")
    ResponseEntity<CustomerResponseDto> saveCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        Customer customer = modelMapper.map(customerRequestDto, Customer.class);
        Customer savedCustomer = customerService.saveCustomer(customer);
        CustomerResponseDto customerResponseDto = modelMapper.map(savedCustomer, CustomerResponseDto.class);

        return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/customer/changePassword/{customerId}/{password}/{confirmingPassword}")
    ResponseEntity<Void> changePassword(@PathVariable Long customerId, @PathVariable String password, @PathVariable String confirmingPassword) {
        customerService.changePassword(customerId, password, confirmingPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
