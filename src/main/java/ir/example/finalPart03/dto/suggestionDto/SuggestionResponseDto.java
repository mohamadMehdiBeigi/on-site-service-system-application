package ir.example.finalPart03.dto.suggestionDto;

import ir.example.finalPart03.model.Suggestions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SuggestionResponseDto {


    private LocalDateTime suggestionsRegistrationDate;

    private Double SuggestedPrice;

    private LocalDateTime suggestedTimeToStartWork;

    private Double durationOfDailyWork;


    public static SuggestionResponseDto suggestionToResponseDto(Suggestions suggestions) {
        return new SuggestionResponseDto(
                suggestions.getSuggestionsRegistrationDate(),
                suggestions.getSuggestedPrice(),
                suggestions.getSuggestedTimeToStartWork(),
                suggestions.getDurationOfDailyWork()
        );
    }
}
