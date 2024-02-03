package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Order extends BaseEntity<Long> implements Serializable {

    Double suggestedPrice;

    String workDescription;

    LocalDateTime startDayOfWork;

    LocalDateTime endTimeOfWork;

    String address;

    @Enumerated(value = EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne
    SubServices subServices;

    @ManyToOne
    Customer customer;

    @OneToOne
    Comments comments;

    @PrePersist
    protected void onCreate() {
        orderStatus = OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST;
    }


}
