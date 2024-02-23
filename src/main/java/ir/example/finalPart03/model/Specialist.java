package ir.example.finalPart03.model;

import ir.example.finalPart03.model.enums.SpecialistStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@AllArgsConstructor
@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Specialist extends Users {

    @Enumerated(value = EnumType.STRING)
    SpecialistStatus specialistStatus;

    @Lob
    byte[] image;

    Double averageScores;

    @ManyToMany
    @JoinTable(
            name = "users_sub_services",
            schema = "final_part3",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_service_id")
    )
    Set<SubServices> subServices;


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
