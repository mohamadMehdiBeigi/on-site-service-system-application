package ir.example.finalPart03.dto.suggestionDto;

import java.time.LocalDateTime;

public class SuggestionResponseDto {

    private Long id;

    private LocalDateTime suggestionsRegistrationDate;

    private Double SuggestedPrice;

    private LocalDateTime suggestedTimeToStartWork;

    private Double durationOfDailyWork;

    public SuggestionResponseDto(Long id, LocalDateTime suggestionsRegistrationDate, Double suggestedPrice, LocalDateTime suggestedTimeToStartWork, Double durationOfDailyWork) {
        this.id = id;
        this.suggestionsRegistrationDate = suggestionsRegistrationDate;
        SuggestedPrice = suggestedPrice;
        this.suggestedTimeToStartWork = suggestedTimeToStartWork;
        this.durationOfDailyWork = durationOfDailyWork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
