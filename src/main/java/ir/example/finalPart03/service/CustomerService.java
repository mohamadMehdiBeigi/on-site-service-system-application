package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Customer;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findByEmail(String email);

    Customer saveCustomer(Customer customer);

    void changePassword(Long id, String password, String oldPassword, String confirmingPassword);

    Boolean checkUniqueEmail(String email, Long customerIdForUpdate);

}
