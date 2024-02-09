package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Specialist;

public interface SpecialistService {

    Specialist saveSpecialist(Specialist specialist);

    Boolean checkingSpecialistStatus(Long specialistId);

    void confirmingSpecialStatus(Long specialistId);

    Specialist changePassword(Long id, String password, String confirmingPassword);

    void saveImageToFile(Long id);

    Boolean checkUniqueEmail(String email, Long specialistIdForUpdate);

    void addSubServiceToSpecialist(Long specialistId, Long subServiceId);

    Specialist seeAverageScore(Long specialistId);


    Specialist findById(Long specialistId);
}
