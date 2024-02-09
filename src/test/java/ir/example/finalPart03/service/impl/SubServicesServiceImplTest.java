package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Services;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.ServicesRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SubServicesRepository;
import ir.example.finalPart03.service.SubServiceService;
import ir.example.finalPart03.util.RandomStringGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class SubServicesServiceImplTest {

    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private SubServicesRepository subServicesRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    private SubServices savedSubService;

    private SubServices subServices;

    private Services savedService;

    private Specialist savedSpecialist;

    @BeforeEach
    void setUp() {
        Specialist specialist = new Specialist();
        specialist.setFirstname("John");
        specialist.setLastname("Doe");
        specialist.setEmail("john.doe" + RandomStringGenerator.randomGenerator() + "@example.com");
        specialist.setSignupDate(LocalDateTime.now());
        specialist.setSpecialistStatus(SpecialistStatus.WAIT_FOR_CONFIRMED);

        savedSpecialist = specialistRepository.save(specialist);

        Services services = new Services(
                "service" + RandomStringGenerator.randomGenerator());

        savedService = servicesRepository.save(services);

        subServices = new SubServices(
                "subService" + RandomStringGenerator.randomGenerator(),
                100.0,
                "some description",
                services,
                null
        );
        savedSubService = subServiceService.saveSubService(subServices, null);
    }

    @Test
    @Transactional
    @Order(1)
    void saveSubService() {
        assertNotNull(savedSubService, "savedSpecialist should not be null.");
        assertEquals(savedSubService, subServices);

    }

    @Test
    @Transactional
    @Order(2)
    void updateBasePriceAndDescription() {
        SubServices foundSubService = subServicesRepository.findById(savedSubService.getId()).get();
        Double oldBasePrice = foundSubService.getBasePrice();
        String oldDescription = foundSubService.getDescription();
        SubServices changedSubService = subServiceService.updateBasePriceAndDescription(foundSubService.getId(), 300.0, "new Description");
        Double newBasePrice = changedSubService.getBasePrice();
        String newDescription = changedSubService.getDescription();
        assertNotNull(changedSubService);
        assertNotEquals(oldBasePrice, newBasePrice);
        assertNotEquals(oldDescription, newDescription);
    }

    @Test
    @Order(3)
    void findAllByServiceId() {
        List<SubServices> allByServiceId = subServiceService.findAllByServiceId(savedService.getId());
        assertNotNull(allByServiceId.get(0).getSubServiceName());
    }

    @Test
    @Order(4)
    void checkUniqueSubServiceName() {
        SubServices foundSubServices = subServicesRepository.findById(savedSubService.getId()).get();
        assertThrows(RuntimeException.class,
                () -> subServiceService.checkUniqueSubServiceName(foundSubServices.getSubServiceName(), foundSubServices.getId() + 1), "The SubServices name you entered is already used .");
        assertThrows(RuntimeException.class,
                () -> subServiceService.checkUniqueSubServiceName(foundSubServices.getSubServiceName(), null), "The SubServices name you entered is already exist. Try another.");

    }

    @Test
    @Order(5)
    @Transactional
    void deleteSubServiceById() {
        SubServices foundSubService = subServicesRepository.findById(savedSubService.getId()).get();
        assertDoesNotThrow(() -> subServiceService.deleteSubServiceById(foundSubService.getId()));

    }

    @Test
    @Order(6)
    @Transactional
    void removeSubServiceRelationalByServiceId() {
        SubServices foundSubService = subServicesRepository.findById(savedSubService.getId()).get();
        Assertions.assertDoesNotThrow(() -> subServiceService.removeSubServiceByServiceId(foundSubService.getId()));

    }

    @Test
    @Order(7)
    @Transactional
    void deleteSubServicesOfSpecialist() {
        Specialist specialist = specialistRepository.findById(savedSpecialist.getId()).get();
        SubServices subServicesNo2 = subServicesRepository.findById(savedSubService.getId()).get();
        subServicesNo2.setId(null);
        subServicesNo2.setSubServiceName("subService" + RandomStringGenerator.randomGenerator());
        SubServices savedNewSubService = subServicesRepository.save(subServicesNo2);
        specialist.setSubServices(new HashSet<>());
        subServicesNo2.setSpecialists(new HashSet<>());
        specialist.getSubServices().add(subServicesNo2);
        specialist.getSubServices().add(savedNewSubService);
        subServicesNo2.getSpecialists().add(specialist);
        savedNewSubService.getSpecialists().add(specialist);
        specialistRepository.save(specialist);
        Assertions.assertDoesNotThrow(() -> subServiceService.deleteSubServicesOfSpecialist(specialist.getId()));


    }

}