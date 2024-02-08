package ir.example.finalPart03.service.impl;


import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.*;
import ir.example.finalPart03.service.OrderService;
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
class OrderServiceImplTest {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private SubServicesRepository subServicesRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    private Order savedOrder;


    @BeforeAll
    void setUp() {

        Specialist specialist = new Specialist();
        specialist.setFirstname("John");
        specialist.setLastname("Doe");
        specialist.setEmail("john.doe" + RandomStringGenerator.randomGenerator() + "@example.com");
        specialist.setSignupDate(LocalDateTime.now());
        specialist.setSpecialistStatus(SpecialistStatus.WAIT_FOR_CONFIRMED);

        Specialist savedSpecialist = specialistRepository.save(specialist);

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
                null, LocalDateTime.now(),
                0.0
        );
        customerRepository.save(customer);

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
        savedOrder = orderService.saveOrder(order, 10.0, null, null);

        subServices.setSpecialists(new HashSet<>());
        specialist.setSubServices(new HashSet<>());
        specialist.getSubServices().add(savedSubService);
        subServices.getSpecialists().add(savedSpecialist);
        specialistRepository.save(savedSpecialist);

    }

    @Test
    void save() {
        assertNotNull(savedOrder, "savedSpecialist should not be null.");
    }

    @org.junit.jupiter.api.Order(1)
    @Test
    void findById() {
        Order order = orderService.findById(savedOrder.getId());
        assertNotNull(order, "order shouldn't be null");
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findStartDateOfWork() {
        LocalDateTime startDateOfWork = orderService.findStartDateOfWork(savedOrder.getId());
        assertNotNull(startDateOfWork, "this shouldn't be null");
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAllByOrderStatusAndSpecialistId() {
        List<Order> allByOrderStatus = orderService.findAllByOrderStatusAndSpecialistId(savedOrder.getId());
        boolean allMatchWaitingForTheSuggestionOfSpecialist = allByOrderStatus.stream().map(Order::getOrderStatus)
                .allMatch(status -> status.equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST));

        assertTrue(allMatchWaitingForTheSuggestionOfSpecialist);
    }

    @Test
    @Transactional
    @org.junit.jupiter.api.Order(4)
    void changeOrderStatusWaitingForSelection() {
        Order order = orderRepository.findById(savedOrder.getId()).get();
        OrderStatus oldOrderStatus = order.getOrderStatus();
        Order changedOrder = orderService.changeOrderStatusWaitingForSelection(order.getId());
        OrderStatus newOrderStatus = changedOrder.getOrderStatus();
        assertEquals(changedOrder.getOrderStatus(), OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        assertNotEquals(newOrderStatus, oldOrderStatus);

    }

    @Test
    @Transactional
    @org.junit.jupiter.api.Order(5)
    void changeOrderStatusToComingToYourPlace() {
        Order order = orderRepository.findById(savedOrder.getId()).get();
        OrderStatus oldOrderStatus = order.getOrderStatus();
        Order changedOrder = orderService.changeOrderStatusToComingToYourPlace(order.getId());
        OrderStatus newOrderStatus = changedOrder.getOrderStatus();
        assertEquals(changedOrder.getOrderStatus(), OrderStatus.WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        assertNotEquals(newOrderStatus, oldOrderStatus);

    }

    @Test
    @Transactional
    @org.junit.jupiter.api.Order(6)
    void changeOrderStatusToStartedByCustomer() {
        Order order = orderRepository.findById(savedOrder.getId()).get();
        order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        OrderStatus oldOrderStatus = order.getOrderStatus();
        Order changedOrder = orderService.changeOrderStatusToStartedByCustomer(order.getId(), order.getCustomer().getId());
        OrderStatus newOrderStatus = changedOrder.getOrderStatus();
        assertEquals(changedOrder.getOrderStatus(), OrderStatus.STARTED);
        assertNotEquals(newOrderStatus, oldOrderStatus);
    }

    @Test
    @Transactional
    @org.junit.jupiter.api.Order(7)
    void changeOrderStatusToDone() {
        Order order = orderRepository.findById(savedOrder.getId()).get();
        order.setOrderStatus(OrderStatus.STARTED);
        OrderStatus oldOrderStatus = order.getOrderStatus();
        Order changedOrder = orderService.changeOrderStatusToDone(order.getId(), order.getCustomer().getId());
        OrderStatus newOrderStatus = changedOrder.getOrderStatus();
        assertEquals(changedOrder.getOrderStatus(), OrderStatus.DONE);
        assertNotEquals(newOrderStatus, oldOrderStatus);
    }
}