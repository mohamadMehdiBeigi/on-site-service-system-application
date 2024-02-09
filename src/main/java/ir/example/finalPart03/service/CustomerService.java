package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    void changePassword(Long id, String password, String confirmingPassword);

    Boolean checkUniqueEmail(String email, Long customerIdForUpdate );

}
