package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;


@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class SubServices extends BaseEntity<Long> implements Serializable {

    @Column(unique = true)
    String subServiceName;

    @Column(nullable = false)
    @Min(value = 0, message = "price cant be lower than zero")
    Double basePrice;

    String description;

    @ManyToOne
    Services services;

    @ManyToMany(mappedBy = "subServices")
    Set<Specialist> specialists;


    public SubServices(String subServiceName, Double basePrice, String description, Services services, Set<Specialist> specialists) {
        this.subServiceName = subServiceName;
        this.basePrice = basePrice;
        this.description = description;
        this.services = services;
        this.specialists = specialists;
    }

    public SubServices() {
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Set<Specialist> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(Set<Specialist> specialists) {
        this.specialists = specialists;
    }
}
