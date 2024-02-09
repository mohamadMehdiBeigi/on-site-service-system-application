package ir.example.finalPart03.dto.suggestionDto;

import ir.example.finalPart03.model.Suggestions;
import ir.example.finalPart03.util.validations.ValidLocalDateTime;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SuggestionRequestDto {

    @ValidLocalDateTime
    private LocalDateTime suggestionsRegistrationDate;

    private Double suggestedPrice;

    @ValidLocalDateTime
    private LocalDateTime suggestedTimeToStartWork;

    @Min(value = 0, message = "cant be lower than zero")
    private Double durationOfDailyWork;

    private Long specialistId;

    private Long orderId;


    public static Suggestions dtoToSuggestion(SuggestionRequestDto suggestionRequestDto){
        Suggestions suggestions = new Suggestions();
        suggestions.setSuggestedPrice(suggestionRequestDto.getSuggestedPrice());
        suggestions.setSuggestionsRegistrationDate(suggestionRequestDto.getSuggestionsRegistrationDate());
        suggestions.setSuggestedTimeToStartWork(suggestionRequestDto.getSuggestedTimeToStartWork());
        suggestions.setDurationOfDailyWork(suggestionRequestDto.getDurationOfDailyWork());
        return suggestions;
    }
//                    suggestionRequestDto.getSuggestionsRegistrationDate(),
//                            suggestionRequestDto.getSuggestedPrice(),
//                            suggestionRequestDto.getSuggestedTimeToStartWork(),
//                            suggestionRequestDto.getDurationOfDailyWork(),

}
