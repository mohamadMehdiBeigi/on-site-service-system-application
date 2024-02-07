package ir.example.finalPart03.dto.suggestionDto;

import ir.example.finalPart03.util.validations.ValidLocalDateTime;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public class SuggestionRequestDto {

    @ValidLocalDateTime
    private LocalDateTime suggestionsRegistrationDate;

    @Min(value = 0, message = "price cant be lower than zero")
    private Double SuggestedPrice;

    @ValidLocalDateTime
    private LocalDateTime suggestedTimeToStartWork;

    @Min(value = 0, message = "cant be lower than zero")
    private Double durationOfDailyWork;

    private Long specialistId;

    private Long orderId;

    public SuggestionRequestDto(LocalDateTime suggestionsRegistrationDate, Double suggestedPrice, LocalDateTime suggestedTimeToStartWork, Double durationOfDailyWork, Long specialistId, Long orderId) {
        this.suggestionsRegistrationDate = suggestionsRegistrationDate;
        SuggestedPrice = suggestedPrice;
        this.suggestedTimeToStartWork = suggestedTimeToStartWork;
        this.durationOfDailyWork = durationOfDailyWork;
        this.specialistId = specialistId;
        this.orderId = orderId;
    }

    public SuggestionRequestDto() {
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

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
