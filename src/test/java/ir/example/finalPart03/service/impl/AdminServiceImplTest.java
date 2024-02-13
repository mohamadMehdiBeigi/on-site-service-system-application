package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.model.Admin;
import ir.example.finalPart03.model.enums.Role;
import ir.example.finalPart03.repository.AdminRepository;
import ir.example.finalPart03.service.AdminService;
import ir.example.finalPart03.util.RandomStringGenerator;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    private Admin savedAdmin;

    @BeforeEach
    void setUp() {
        Admin admin = new Admin(
                "admin",
                "admin",
                "admin" + RandomStringGenerator.randomGenerator() + "@gmail.com",
                "admin",
                LocalDateTime.now(),
                Role.ROLE_ADMIN

        );
//        ValidationUtil.validate(admin);
        savedAdmin = adminRepository.save(admin);
    }


    @Test
    void checkUniqueEmailThrowsException() {
        Admin admin = adminRepository.findById(savedAdmin.getId()).get();
        assertThrows(EntityExistsException.class, () -> adminService.checkUniqueEmail(admin.getEmail()));
    }
}