package ir.example.finalPart03.dto.specialistDto;

import ir.example.finalPart03.model.enums.SpecialistStatus;

import java.time.LocalDateTime;

public class SpecialistResponseDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    LocalDateTime signupDate;

    private Double averageScores;

    private SpecialistStatus specialistStatus;

    public SpecialistResponseDto(Long id, String firstname, String lastname, String email, byte[] image, LocalDateTime signupDate, Double averageScores, SpecialistStatus specialistStatus) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.signupDate = signupDate;
        this.averageScores = averageScores;
        this.specialistStatus = specialistStatus;
    }

    public SpecialistResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDateTime signupDate) {
        this.signupDate = signupDate;
    }

    public Double getAverageScores() {
        return averageScores;
    }

    public void setAverageScores(Double averageScores) {
        this.averageScores = averageScores;
    }

    public SpecialistStatus getSpecialistStatus() {
        return specialistStatus;
    }

    public void setSpecialistStatus(SpecialistStatus specialistStatus) {
        this.specialistStatus = specialistStatus;
    }
}
