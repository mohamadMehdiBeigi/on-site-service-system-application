package ir.example.finalPart03.controller;

import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/customer/save")
    ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/login/customer/{email}/{password}")
    public ResponseEntity<Customer> findByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        return new ResponseEntity<>(customerService.findByEmailAndPassword(email, password), HttpStatus.OK);
    }

    @PutMapping("/customer/changePassword/{customerId}/{password}/{confirmingPassword}")
    ResponseEntity<Void> changePassword(@PathVariable Long customerId, @PathVariable String password, @PathVariable String confirmingPassword) {
        customerService.changePassword(customerId, password, confirmingPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
