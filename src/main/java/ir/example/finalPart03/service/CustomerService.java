package ir.example.finalPart03.service;


import ir.example.finalPart03.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {

    Optional<Customer> findByEmail(String email);

    Customer saveCustomer(Customer customer);

    void changePassword(Long id, String password, String oldPassword, String confirmingPassword);

    Boolean checkUniqueEmail(String email, Long customerIdForUpdate);

}
