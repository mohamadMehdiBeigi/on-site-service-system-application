package ir.example.finalPart03.service;


import ir.example.finalPart03.model.Suggestions;

import java.util.List;

public interface SuggestionService {
    Suggestions saveSuggestion(Suggestions suggestions, Long orderId, Long specialistId);

    List<Suggestions> findAllByCustomerIdOrOrderBySuggestedPrice(Long customerId);


    List<Suggestions> findAllByCustomerIdOrOrderByTotalScores(Long customerId);

    Long choosingSuggestionByCustomer(Long suggestionId);

}
