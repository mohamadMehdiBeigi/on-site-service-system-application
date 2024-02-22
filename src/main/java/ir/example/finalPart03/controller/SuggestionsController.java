package ir.example.finalPart03.controller;

import ir.example.finalPart03.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suggestion")
@AllArgsConstructor
public class SuggestionsController {

    SuggestionService suggestionService;

    ModelMapper modelMapper;




}
