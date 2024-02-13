package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.*;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.*;
import ir.example.finalPart03.service.SuggestionService;
import ir.example.finalPart03.util.RandomStringGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SuggestionsServiceImplTest {

    @Autowired
    SuggestionService suggestionService;

    @Autowired
    SuggestionsRepository suggestionsRepository;

    @Autowired
    SpecialistRepository specialistRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    SubServicesRepository subServicesRepository;

    Suggestions savedSuggestion;

    Suggestions suggestions;

    Customer savedCustomer;


    @BeforeAll
    void setUp() {
        Specialist specialist = new Specialist();
        specialist.setFirstname("John");
        specialist.setLastname("Doe");
        specialist.setEmail("john.doe" + RandomStringGenerator.randomGenerator() + "@example.com");
        specialist.setSignupDate(LocalDateTime.now());
        specialist.setAverageScores(1.0);
        specialist.setSpecialistStatus(SpecialistStatus.WAIT_FOR_CONFIRMED
        );
        Specialist saveSpecialist = specialistRepository.save(specialist);

        Services services = new Services(
                "service" + RandomStringGenerator.randomGenerator());

        servicesRepository.save(services);

        SubServices subServices = new SubServices(
                "subService" + RandomStringGenerator.randomGenerator(),
                100.0,
                "some description",
                services,
                null
        );
        SubServices savedSubService = subServicesRepository.save(subServices);

        Customer customer = new Customer(
                "ali",
                "alavi",
                "alavi" + RandomStringGenerator.randomGenerator() + "@gmail.com",
                null, LocalDateTime.now()
        );
        savedCustomer = customerRepository.save(customer);

        Order order = new Order(
                1000.0,
                "description",
                LocalDateTime.of(2024, 3, 3, 3, 3, 3),
                null,
                "address",
                OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST,
                subServices,
                customer,
                null
        );
        orderRepository.save(order);


        suggestions = new Suggestions(
                LocalDateTime.now(),
                400.0,
                LocalDateTime.of(2024, 2, 2, 2, 2),
                1.5,
                specialist,
                order

        );
        subServices.setSpecialists(new HashSet<>());
        specialist.setSubServices(new HashSet<>());
        specialist.getSubServices().add(savedSubService);
        subServices.getSpecialists().add(saveSpecialist);
        specialistRepository.save(saveSpecialist);
        savedSuggestion = suggestionService.saveSuggestion(suggestions, null, null);


    }

    @Test
    @Transactional
    @org.junit.jupiter.api.Order(1)
    void saveSuggestionAndReturnOrderId() {
        assertNotNull(savedSuggestion);
        assertEquals(suggestions, savedSuggestion);

    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findAllByCustomerIdOrOrderBySuggestedPrice() {
        Suggestions foundSuggestion = suggestionsRepository.findById(savedSuggestion.getId()).get();
        foundSuggestion.setSuggestedPrice(500.0);
        foundSuggestion.setId(null);
        suggestionsRepository.save(foundSuggestion);
        List<Suggestions> allByCustomerIdOrOrderBySuggestedPrice = suggestionService.findAllByCustomerIdOrOrderBySuggestedPrice(savedCustomer.getId());
        assertNotNull(allByCustomerIdOrOrderBySuggestedPrice.get(0));
        assertNotNull(allByCustomerIdOrOrderBySuggestedPrice.get(1));
        assertTrue(allByCustomerIdOrOrderBySuggestedPrice.get(0).getSuggestedPrice() < allByCustomerIdOrOrderBySuggestedPrice.get(1).getSuggestedPrice());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAllByCustomerIdOrOrderByTotalScores() {
        Suggestions foundSuggestion = suggestionsRepository.findById(savedSuggestion.getId()).get();
        Specialist specialist = foundSuggestion.getSpecialist();
        specialist.setAverageScores(2.0);
        specialist.setId(null);
        specialist.setEmail("john.doe" + RandomStringGenerator.randomGenerator() + "@example.com");
        Specialist savedSpecialist = specialistRepository.save(specialist);
        foundSuggestion.setSpecialist(savedSpecialist);
        suggestionsRepository.save(foundSuggestion);
        List<Suggestions> allByCustomerIdOrOrderByTotalScores = suggestionService.findAllByCustomerIdOrOrderByTotalScores(savedCustomer.getId());
        assertNotNull(allByCustomerIdOrOrderByTotalScores.get(0));
        assertNotNull(allByCustomerIdOrOrderByTotalScores.get(1));
        assertTrue(allByCustomerIdOrOrderByTotalScores.get(0).getSpecialist().getAverageScores() < allByCustomerIdOrOrderByTotalScores.get(1).getSpecialist().getAverageScores());

    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void choosingSuggestionByCostumer() {
        Long orderId = suggestionService.choosingSuggestionByCustomer(savedSuggestion.getId());
        assertNotNull(orderId);
    }
}