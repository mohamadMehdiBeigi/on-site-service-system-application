package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.model.Suggestions;
import ir.example.finalPart03.model.baseModel.BaseEntity;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SuggestionsRepository;
import ir.example.finalPart03.service.SuggestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class SuggestionsServiceImpl implements SuggestionService {

    private SuggestionsRepository suggestionsRepository;

    private OrderRepository orderRepository;

    private SpecialistRepository specialistRepository;

    public SuggestionsServiceImpl(SuggestionsRepository suggestionsRepository, OrderRepository orderRepository) {
        this.suggestionsRepository = suggestionsRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Suggestions saveSuggestion(Suggestions suggestions, Long orderId, Long specialistId) {
        try {
            List<Order> allByOrderStatusAndSpecialistId = orderRepository.findAllByOrderStatusAndSpecialistId(suggestions.getSpecialist().getId());

            Order order = null;
            if (suggestions.getOrder() == null){
                order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new NotFoundException("this order id is not exist"));
                suggestions.setOrder(order);
            }

            if (suggestions.getSpecialist() == null){
                Specialist specialist = specialistRepository.findById(specialistId)
                        .orElseThrow(() -> new NotFoundException("this specialist id is not exist"));
                suggestions.setSpecialist(specialist);
            }

            if (suggestions.getOrder().getOrderStatus() != OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST &&
                    suggestions.getOrder().getOrderStatus() != (OrderStatus.WAITING_FOR_SPECIALIST_SELECTION)) {
                throw new BadRequestException("order status must be WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST or WAITING_FOR_SPECIALIST_SELECTION");
            }
            List<Long> collect = allByOrderStatusAndSpecialistId.stream().map(BaseEntity::getId).toList();
            if (!collect.contains(suggestions.getOrder().getId())) {
                throw new BadRequestException("you must only select Orders that are related to you");
            }

            if (suggestions.getSuggestedTimeToStartWork().isBefore(suggestions.getOrder().getStartDayOfWork())) {
                throw new BadRequestException("suggested Time to start work is before" + suggestions.getOrder().getStartDayOfWork() + "\n" +
                        "please type a valid time");
            }
            if (suggestions.getSuggestedTimeToStartWork().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("suggested Time to start work is before current time +\n" +
                        "please type a valid time");
            }
            assert order != null;
            if (suggestions.getSuggestedPrice() < order.getSuggestedPrice()) {
                throw new BadRequestException("orderPrice cant be less than your suggested price,try equal or more price");
            }
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

