package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.DuplicateException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.repository.CustomerRepository;
import ir.example.finalPart03.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public Customer saveCustomer(Customer customer) {

        if (!checkUniqueEmail(customer.getEmail(), customer.getId())) {
            throw new DuplicateException("Email already exists");
        }
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new BadRequestException("Can't save or update customer data: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void changePassword(Long id, String oldPassword, String password, String confirmingPassword) {
        if (!Objects.equals(password, confirmingPassword)) {
            throw new DuplicateException("password and confirming password is not the same");
        }
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("this customerId is not found!"));
        if (!Objects.equals(oldPassword, customer.getPassword())) {
            throw new BadRequestException("your oldPassword is incorrect, please enter your valid old password \n");
        }

        customer.setPassword(password);
        try {
            customerRepository.save(customer);

        } catch (Exception e) {
            throw new BadRequestException("invalid input for change customers password" + e.getMessage());
        }
    }

    @Override
    public Boolean checkUniqueEmail(String email, Long customerIdForUpdate) {
        if (customerIdForUpdate == null) {
            Integer checked = customerRepository.checkUniqueEmailForNewCustomer(email);
            if (checked > 0) {
                throw new DuplicateException("The email you entered is already exist. Try another email.");
            }
        } else {
            Integer checked = customerRepository.checkUniqueEmailForExistingCustomer(email, customerIdForUpdate);
            if (checked > 0) {
                throw new DuplicateException("The email you entered is already used by another customer.");
            }
        }
        return true;
    }
}
