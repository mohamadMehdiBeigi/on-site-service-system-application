package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.util.validations.ValidLocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;
import java.time.LocalDateTime;


//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@SuperBuilder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
//@SequenceGenerator(name = "ID_GENERATOR", allocationSize = 1, sequenceName = "seq_suggestions", schema = "final_part2")
public class Suggestions extends BaseEntity<Long> implements Serializable {

    @ValidLocalDateTime
    LocalDateTime suggestionsRegistrationDate;

    @Min(value = 0, message = "price cant be lower than zero")
    Double SuggestedPrice;

    @ValidLocalDateTime
    LocalDateTime suggestedTimeToStartWork;

    @Min(value = 0, message = "cant be lower than zero")
    Double durationOfDailyWork;

    @ManyToOne
    Specialist specialist;

    @ManyToOne
    Order order;


    public Suggestions() {
    }

    public Suggestions(LocalDateTime suggestionsRegistrationDate, Double suggestedPrice, LocalDateTime suggestedTimeToStartWork, Double durationOfDailyWork, Specialist specialist, Order order) {
        this.suggestionsRegistrationDate = suggestionsRegistrationDate;
        SuggestedPrice = suggestedPrice;
        this.suggestedTimeToStartWork = suggestedTimeToStartWork;
        this.durationOfDailyWork = durationOfDailyWork;
        this.specialist = specialist;
        this.order = order;
    }

    public LocalDateTime getSuggestionsRegistrationDate() {
        return suggestionsRegistrationDate;
    }

    public void setSuggestionsRegistrationDate(LocalDateTime suggestionsRegistrationDate) {
        this.suggestionsRegistrationDate = suggestionsRegistrationDate;
    }

    public Double getSuggestedPrice() {
        return SuggestedPrice;
    }

    public void setSuggestedPrice(Double suggestedPrice) {
        SuggestedPrice = suggestedPrice;
    }

    public LocalDateTime getSuggestedTimeToStartWork() {
        return suggestedTimeToStartWork;
    }

    public void setSuggestedTimeToStartWork(LocalDateTime suggestedTimeToStartWork) {
        this.suggestedTimeToStartWork = suggestedTimeToStartWork;
    }

    public Double getDurationOfDailyWork() {
        return durationOfDailyWork;
    }

    public void setDurationOfDailyWork(Double durationOfDailyWork) {
        this.durationOfDailyWork = durationOfDailyWork;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
