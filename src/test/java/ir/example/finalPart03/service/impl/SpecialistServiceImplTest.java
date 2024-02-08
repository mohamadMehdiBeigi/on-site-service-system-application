package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Services;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.ServicesRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SubServicesRepository;
import ir.example.finalPart03.util.ImageReader;
import ir.example.finalPart03.util.RandomStringGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpecialistServiceImplTest {

    @Autowired
    private SpecialistServiceImpl specialistService;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private SubServicesRepository subServicesRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    private Specialist savedSpecialist;




    @BeforeEach
    void init() throws IOException {

        Specialist specialist = new Specialist();
        specialist.setFirstname("John");
        specialist.setLastname("Doe");
        specialist.setEmail("john.doe" + RandomStringGenerator.randomGenerator() + "@example.com");
        specialist.setSignupDate(LocalDateTime.now());
        specialist.setCredit(0.0);
        specialist.setSpecialistStatus(SpecialistStatus.WAIT_FOR_CONFIRMED);
        String filePath = "C:\\Users\\Data\\Pictures\\l.jpg";
        specialist.setImage(ImageReader
                .uploadProfilePicture(
                        filePath, specialist.getFirstname(), specialist.getLastname(), specialist.getEmail()
                ));
        savedSpecialist = specialistService.saveSpecialist(specialist);
        assertNotNull(savedSpecialist, "savedSpecialist should not be null.");
        assertEquals(savedSpecialist, specialist);

    }


    @Test
    @Order(1)
    void findByEmailAndPassword() {
        Specialist byEmailAndPassword = specialistService.findByEmailAndPassword(savedSpecialist.getEmail(), savedSpecialist.getPassword());
        assertNotNull(byEmailAndPassword, "this shouldn't be null");
    }

    @Test
    @Order(2)
    void confirmingSpecialStatus() {
        specialistService.confirmingSpecialStatus(savedSpecialist.getId());
        Specialist updatedSpecialist = specialistRepository.findById(savedSpecialist.getId()).get();
        assertEquals(SpecialistStatus.CONFIRMED, updatedSpecialist.getSpecialistStatus(), "Specialist status should be updated to CONFIRMED");

    }

    @Test
    @Order(3)
    void checkingSpecialistStatus() {
        Boolean specialistStatus = specialistService.checkingSpecialistStatus(savedSpecialist.getId());
        assertFalse(specialistStatus);
    }

    @Test
    @Order(4)
    void saveImageToFile() {
        Specialist foundSpecialist = specialistRepository.findById(savedSpecialist.getId()).get();
        specialistService.saveImageToFile(foundSpecialist.getId());
        String baseDirectory = "C:\\Users\\Data\\IdeaProjects\\finalPart2\\src\\main\\resources\\extractedImage";
        String fileName = "id_number" + foundSpecialist.getId() + ".jpg";
        boolean exists = Files.exists(Paths.get(baseDirectory, fileName));
        assertTrue(exists);
    }

    @Test
    @Order(5)
    void checkUniqueEmail() {
        Specialist specialist = specialistRepository.findById(savedSpecialist.getId()).get();
        assertThrows(RuntimeException.class, () -> specialistService.checkUniqueEmail(specialist.getEmail(), specialist.getId() + 1), "The email you entered is already used by another customer.");
        assertThrows(RuntimeException.class, () -> specialistService.checkUniqueEmail(specialist.getEmail(), null), "The email you entered is already exist. Try another email.");
    }

    @Test
    @Transactional
    @Order(6)
    void addSubServiceToSpecialist() {
        Services services = new Services("cleaning2");
        Services saveService = servicesRepository.save(services);
        SubServices subServices = new SubServices("cleaningGarage", 1000.0, "no", saveService, null);
        SubServices subServicesFinal = subServicesRepository.save(subServices);
        specialistService.addSubServiceToSpecialist(savedSpecialist.getId(), subServicesFinal.getId());
        Specialist foundSpecialist = specialistRepository.findById(savedSpecialist.getId()).get();
        assertTrue(foundSpecialist.getSubServices().contains(subServicesFinal));
    }

    @Test
    @Transactional
    @Order(7)
    void changePassword() {
        Specialist specialist = specialistRepository.findById(savedSpecialist.getId()).get();
        String password = specialist.getPassword();
        specialistService.changePassword(specialist.getId(), "changedPassword1", "changedPassword1");
        Specialist changedSpecialist = specialistRepository.findById(specialist.getId()).get();
        String newPassword = changedSpecialist.getPassword();
        assertNotNull(changedSpecialist, "changedSpecialist shouldn;t be null!");
        assertNotEquals(password, newPassword);
    }
}