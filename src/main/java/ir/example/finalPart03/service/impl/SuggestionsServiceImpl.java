package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.Suggestions;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SuggestionsRepository;
import ir.example.finalPart03.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class SuggestionsServiceImpl implements SuggestionService {

    private SuggestionsRepository suggestionsRepository;

    private OrderRepository orderRepository;

    private SpecialistRepository specialistRepository;


    @Transactional
    @Override
    public Suggestions saveSuggestion(Suggestions suggestions, Long orderId, Long specialistId) {

            if (suggestions.getOrder() == null){
                Order order = new Order();
                order.setId(orderId);
                suggestions.setOrder(order);
            }

            if (suggestions.getSpecialist() == null){
                Specialist specialist = new Specialist();
                specialist.setId(specialistId);
                suggestions.setSpecialist(specialist);
            }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("this order id is not exist"));


        if (suggestions.getSuggestedPrice() < order.getSuggestedPrice()) {
                throw new BadRequestException("orderPrice cant be less than your suggested price,try equal or more price");
            }
        try {
            return suggestionsRepository.save(suggestions);

        } catch (Exception e) {
            throw new BadRequestException("there is error with extracting order id" + e.getMessage());
        }
    }

    @Override
    public List<Suggestions> findAllByCustomerIdOrOrderBySuggestedPrice(Long customerId) {
        try {
            return suggestionsRepository.findAllByCustomerIdOrderBySuggestedPrice(customerId);
        } catch (Exception e) {
            throw new NotFoundException("this id has no suggestions from specialist" + e.getMessage());
        }
    }

    @Override
    public List<Suggestions> findAllByCustomerIdOrOrderByTotalScores(Long customerId) {
        try {
            return suggestionsRepository.findAllByCustomerIdOrderByTotalScores(customerId);
        } catch (Exception e) {
            throw new NotFoundException("this id has no suggestions from specialist" + e.getMessage());
        }
    }

    @Override
    public Long choosingSuggestionByCustomer(Long suggestionId) {
        Suggestions suggestions = suggestionsRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("invalid suggestionId"));
        return suggestions.getOrder().getId();
    }
}

