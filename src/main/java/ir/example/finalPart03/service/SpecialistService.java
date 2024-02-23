package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Specialist;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface SpecialistService {

    Optional<Specialist> findByEmail(String email);

    Specialist saveSpecialist(Specialist specialist, MultipartFile file);

    Boolean checkingSpecialistStatus(Long specialistId);

    void confirmingSpecialStatus(Long specialistId);

    Specialist changePassword(Long id, String oldPassword, String password, String confirmingPassword);

    void saveImageToFile(Long id);

    Boolean checkUniqueEmail(String email, Long specialistIdForUpdate);

    void addSubServiceToSpecialist(Long specialistId, Long subServiceId);

    Specialist seeAverageScore(Long specialistId);


    Specialist findById(Long specialistId);

    void deleteSpecialist(Long specialistId);

}
