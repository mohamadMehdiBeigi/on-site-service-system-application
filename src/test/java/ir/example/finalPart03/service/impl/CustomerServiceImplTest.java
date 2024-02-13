package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.repository.CustomerRepository;
import ir.example.finalPart03.service.CustomerService;
import ir.example.finalPart03.util.RandomStringGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer savedCustomer;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                "ali",
                "alavi",
                "alavi" + RandomStringGenerator.randomGenerator() + "@gmail.com",
                null, LocalDateTime.now());
        savedCustomer = customerService.saveCustomer(customer);

    }

    @Test
    @Order(1)
    @Transactional
    void saveCustomer() {
        assertNotNull(savedCustomer, "savedSpecialist should not be null.");
        assertEquals(savedCustomer, customer);
    }


    @Test
    @Order(2)
    void changePassword() {
        Customer customer = customerRepository.findById(savedCustomer.getId()).get();
        customer.setPassword(RandomStringGenerator.randomGenerator());
        customerService.changePassword(customer.getId(),customer.getPassword(), "changedPassword", "changedPassword");
        Customer changedCustomer = customerRepository.findById(savedCustomer.getId()).get();
        assertNotNull(changedCustomer, "changedSpecialist shouldn;t be null!");
        assertNotEquals(changedCustomer.getPassword(), customer.getPassword());
    }

    @Test
    @Order(3)
    void checkUniqueEmail() {
        Customer customer = customerRepository.findById(savedCustomer.getId()).get();
        assertThrows(RuntimeException.class,
                () -> customerService.checkUniqueEmail(customer.getEmail(), customer.getId() + 1), "The email you entered is already used by another customer.");
        assertThrows(RuntimeException.class,
                () -> customerService.checkUniqueEmail(customer.getEmail(), null), "The email you entered is already exist. Try another email.");

    }
}