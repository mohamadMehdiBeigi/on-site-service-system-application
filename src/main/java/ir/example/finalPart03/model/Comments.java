package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;


@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Comments extends BaseEntity<Long> implements Serializable {

    @Range(min = 0, max = 5, message = "score should've been between 0 to 5")
    @Column(nullable = false)
    Double score;

    String comment;

    @OneToOne
    Order order;

    public Comments(Long aLong, Double score, String comment, Order order) {
        super(aLong);
        this.score = score;
        this.comment = comment;
        this.order = order;
    }

    public Comments() {
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
