package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.suggestionDto.SuggestionRequestDto;
import ir.example.finalPart03.dto.suggestionDto.SuggestionResponseDto;
import ir.example.finalPart03.model.Suggestions;
import ir.example.finalPart03.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/suggestion")
@AllArgsConstructor
public class SuggestionsController {

    SuggestionService suggestionService;

    ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<SuggestionResponseDto> saveSuggestion(@RequestBody SuggestionRequestDto suggestionRequestDto) {
        Suggestions suggestions = SuggestionRequestDto.dtoToSuggestion(suggestionRequestDto);
        Suggestions saveSuggestion = suggestionService.saveSuggestion(suggestions, suggestionRequestDto.getOrderId(), suggestionRequestDto.getSpecialistId());
        SuggestionResponseDto suggestionResponseDto = SuggestionResponseDto.suggestionToResponseDto(saveSuggestion);
        return new ResponseEntity<>(suggestionResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/findAllByCustomerIdOrOrderBySuggestedPrice/{customerId}")
    public ResponseEntity<List<SuggestionResponseDto>> findAllByCustomerIdOrOrderBySuggestedPrice(@PathVariable Long customerId) {
        List<Suggestions> allByCustomerIdOrOrderBySuggestedPrice = suggestionService.findAllByCustomerIdOrOrderBySuggestedPrice(customerId);
        List<SuggestionResponseDto> suggestionResponseDtoList = new ArrayList<>();
        for (Suggestions suggestion :
                allByCustomerIdOrOrderBySuggestedPrice) {
            SuggestionResponseDto suggestionResponseDto = modelMapper.map(suggestion, SuggestionResponseDto.class);
            suggestionResponseDtoList.add(suggestionResponseDto);
        }
        return new ResponseEntity<>(suggestionResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findAllByCustomerIdOrOrderByTotalScores/{customerId}")
    public ResponseEntity<List<SuggestionResponseDto>> findAllByCustomerIdOrOrderByTotalScores(@PathVariable Long customerId){
        List<Suggestions> allByCustomerIdOrOrderByTotalScores = suggestionService.findAllByCustomerIdOrOrderByTotalScores(customerId);
        List<SuggestionResponseDto> suggestionResponseDtoList = new ArrayList<>();
        for (Suggestions suggestion :
                allByCustomerIdOrOrderByTotalScores) {
            SuggestionResponseDto suggestionResponseDto = modelMapper.map(suggestion, SuggestionResponseDto.class);
            suggestionResponseDtoList.add(suggestionResponseDto);
        }
        return ResponseEntity.ok(suggestionResponseDtoList);

    }
}
