package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Comments extends BaseEntity<Long> implements Serializable {

    @Range(min = 0, max = 5, message = "score should've been between 0 to 5")
    @Column(nullable = false)
    Double score;

    String comment;

    @OneToOne(mappedBy = "comments")
    Order order;


}
