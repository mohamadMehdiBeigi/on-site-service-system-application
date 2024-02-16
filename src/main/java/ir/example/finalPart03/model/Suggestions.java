package ir.example.finalPart03.model;


import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.util.validations.ValidLocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;



@Entity
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "final_part3")
public class Suggestions extends BaseEntity<Long> {

    @ValidLocalDateTime
    LocalDateTime suggestionsRegistrationDate;

    @Min(value = 0, message = "price cant be lower than zero")
    Double suggestedPrice;

    @ValidLocalDateTime
    LocalDateTime suggestedTimeToStartWork;

    @Min(value = 0, message = "cant be lower than zero")
    Double durationOfDailyWork;

    @ManyToOne(fetch = FetchType.LAZY)
    Specialist specialist;

    @ManyToOne(fetch = FetchType.LAZY)
    Order order;


    public Suggestions() {
    }

    public Suggestions(LocalDateTime suggestionsRegistrationDate, Double suggestedPrice, LocalDateTime suggestedTimeToStartWork, Double durationOfDailyWork, Specialist specialist, Order order) {
        this.suggestionsRegistrationDate = suggestionsRegistrationDate;
        this.suggestedPrice = suggestedPrice;
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
        return suggestedPrice;
    }

    public void setSuggestedPrice(Double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
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
