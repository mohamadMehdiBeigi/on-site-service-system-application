package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Services;
import ir.example.finalPart03.repository.ServicesRepository;
import ir.example.finalPart03.service.ServicesService;
import ir.example.finalPart03.util.RandomStringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServicesServiceImplTest {

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    ServicesService servicesService;

    Services savedService;

    Services services;

    @BeforeEach
    void init() {
        services = new Services(
                "service" + RandomStringGenerator.randomGenerator()
        );
        savedService = servicesService.saveService(services);
    }

    @Test
    void saveService() {
        assertNotNull(savedService);
        assertEquals(savedService.getServiceName(), services.getServiceName());
    }

    @Test
    void checkUniqueServiceName() {
        Services foundServices = servicesRepository.findById(savedService.getId()).get();
        assertThrows(RuntimeException.class,
                () -> servicesService.checkUniqueServiceName(foundServices.getServiceName(), foundServices.getId() + 1), "The ServiceName you entered is already used.");
        assertThrows(RuntimeException.class,
                () -> servicesService.checkUniqueServiceName(foundServices.getServiceName(), null), "The ServiceName you entered is already exist. Try another.");


    }

    @Test
    void deleteServiceById() {
    }
}