package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.DuplicateException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.dto.registerations.RegistrationRequest;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.enums.Role;
import ir.example.finalPart03.repository.CustomerRepository;
import ir.example.finalPart03.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RegistrationService registrationService;

    private final ModelMapper modelMapper;



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

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Customer savedCustomer;
        try {
            savedCustomer = customerRepository.save(customer);
        } catch (Exception e) {
            throw new BadRequestException("Can't save or update customer data: " + e.getMessage());
        }
        try {
            RegistrationRequest registrationRequest = modelMapper.map(customer, RegistrationRequest.class);
            registrationService.register(registrationRequest, Role.ROLE_CUSTOMER);
        } catch (Exception e){
            throw new NotFoundException("there is problem with email verification" + e.getMessage());
        }
            return savedCustomer;
    }


    @Transactional
    @Override
    public void changePassword(Long id, String oldPassword, String password, String confirmingPassword) {
        if (!Objects.equals(password, confirmingPassword)) {
            throw new DuplicateException("password and confirming password is not the same");
        }
        String encodeOldPassword = passwordEncoder.encode(oldPassword);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("this customerId is not found!"));
        if (!Objects.equals(encodeOldPassword, customer.getPassword())) {
            throw new BadRequestException("your oldPassword is incorrect, please enter your valid old password \n");
        }
        String encode = passwordEncoder.encode(password);
        customer.setPassword(encode);
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
