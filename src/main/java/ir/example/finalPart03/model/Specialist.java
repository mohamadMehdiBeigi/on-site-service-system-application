package ir.example.finalPart03.model;

import ir.example.finalPart03.model.enums.SpecialistStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Specialist extends Users  {

    @Enumerated(value = EnumType.STRING)
    SpecialistStatus specialistStatus;

    @Lob
    private byte[] image;

    Double averageScores;

    @ManyToMany
    @JoinTable(
            name = "users_sub_services",
            schema = "final_part3",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id")
    )
    Set<SubServices> subServices;

    public Specialist(@Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "firstname must have been included just English alphabet") String firstname, @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "lastname must have been include just English alphabet") String lastname, @Email(message = "email syntax is incorrect") String email, @Pattern(regexp = "^\\S+$", message = "password cant have space.") String password, LocalDateTime signupDate, SpecialistStatus specialistStatus, byte[] image, Double averageScores, Set<SubServices> subServices) {
        super(firstname, lastname, email, password, signupDate);
        this.specialistStatus = specialistStatus;
        this.image = image;
        this.averageScores = averageScores;
        this.subServices = subServices;
    }

    public Specialist() {
    }

    public SpecialistStatus getSpecialistStatus() {
        return specialistStatus;
    }

    public void setSpecialistStatus(SpecialistStatus specialistStatus) {
        this.specialistStatus = specialistStatus;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Double getAverageScores() {
        return averageScores;
    }

    public void setAverageScores(Double averageScores) {
        this.averageScores = averageScores;
    }

    public Set<SubServices> getSubServices() {
        return subServices;
    }

    public void setSubServices(Set<SubServices> subServices) {
        this.subServices = subServices;
    }
}
