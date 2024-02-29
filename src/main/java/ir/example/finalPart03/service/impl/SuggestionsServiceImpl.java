package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.Suggestions;
import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.repository.SuggestionsRepository;
import ir.example.finalPart03.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionsServiceImpl implements SuggestionService {

    private final SuggestionsRepository suggestionsRepository;

    private final OrderRepository orderRepository;


    @Transactional
    @Override
    public Suggestions saveSuggestion(Suggestions suggestions, Long orderId, Long specialistId) {

        if (suggestions.getOrder() == null) {
            Order order = new Order();
            order.setId(orderId);
            suggestions.setOrder(order);
        }

        if (suggestions.getSpecialist() == null) {
            Specialist specialist = new Specialist();
            specialist.setId(specialistId);
            suggestions.setSpecialist(specialist);
        }
        if (suggestions.getSuggestionsRegistrationDate() == null) {
            suggestions.setSuggestionsRegistrationDate(LocalDateTime.now());
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("this order id is not exist"));

        List<Order> allByOrderStatusAndSpecialistId = orderRepository.findAllByOrderStatusAndSpecialistId(specialistId);

        List<Long> collect = allByOrderStatusAndSpecialistId.stream().map(BaseEntity::getId).toList();
        if (collect.isEmpty()) {
            throw new BadRequestException("you must only select Orders that are related to you");
        }

        if (order.getOrderStatus() != OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST &&
                order.getOrderStatus() != (OrderStatus.WAITING_FOR_SPECIALIST_SELECTION)) {
            throw new BadRequestException("order status must be WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST or WAITING_FOR_SPECIALIST_SELECTION");
        }

        if (suggestions.getSuggestedPrice() < order.getSuggestedPrice()) {
            throw new BadRequestException("orderPrice cant be less than your suggested price,try equal or more price");
        }
        if (suggestions.getSuggestedTimeToStartWork().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("suggested Time to start work is before current time ." +
                    "please type a valid time");
        }
        if (suggestions.getSuggestedTimeToStartWork().isBefore(order.getStartDayOfWork())) {
            throw new BadRequestException("suggested Time to start work is before start day of work in order :" + order.getStartDayOfWork() +
                    " please type a valid time");
        }
        Suggestions saveSuggestion;
        try {
            saveSuggestion = suggestionsRepository.save(suggestions);
            order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
            orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("there is error with saving suggestion \n" + e.getMessage());
        }
        return saveSuggestion;
    }

    @Override
    public List<Suggestions> findAllByCustomerIdOrOrderBySuggestedPrice(Long customerId) {
        try {
            return suggestionsRepository.findAllByCustomerIdOrderBySuggestedPrice(customerId);
        } catch (Exception e) {
            throw new NotFoundException("this id has no suggestions from specialist \n" + e.getMessage());
        }
    }

    @Override
    public List<Suggestions> findAllByCustomerIdOrOrderByTotalScores(Long customerId) {
        try {
            return suggestionsRepository.findAllByCustomerIdOrderByTotalScores(customerId);
        } catch (Exception e) {
            throw new NotFoundException("this id has no suggestions from specialist \n" + e.getMessage());
        }
    }

    @Override
    public Long choosingSuggestionByCustomer(Long suggestionId) {
        Suggestions suggestions = suggestionsRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("invalid suggestionId"));
        return suggestions.getOrder().getId();
    }
}

