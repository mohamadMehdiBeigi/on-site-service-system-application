package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class SubServices extends BaseEntity<Long> implements Serializable {

    @Column(unique = true)
    String subServiceName;

    @Column(nullable = false)
    @Min(value = 0, message = "price cant be lower than zero")
    Double basePrice;

    String description;

    @ManyToOne(optional = false)
    Services services;

    @ManyToMany(mappedBy = "subServices", cascade = CascadeType.REMOVE)
    Set<Specialist> specialists;

}
