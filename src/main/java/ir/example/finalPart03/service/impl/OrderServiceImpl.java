package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SuggestionsRepository;
import ir.example.finalPart03.service.OrderService;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private SpecialistRepository specialistRepository;

    private SuggestionsRepository suggestionsRepository;


    @Override
    public Order saveOrder(Order order, Double basePrice, Long subServicesId, Long customerId) {
        if (order.getSuggestedPrice() < basePrice) {
            throw new BadRequestException("suggested price is less than base price ");
        }
        if (order.getStartDayOfWork().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("your start day of work is before current date and time");
        }
        try {
            if (order.getOrderStatus() == null && subServicesId != null){
                SubServices subServices = new SubServices();
                subServices.setId(subServicesId);
                order.setSubServices(subServices);
            }
            if (order.getOrderStatus() == null && customerId != null){
                Customer customer = new Customer();
                customer.setId(customerId);
                order.setCustomer(customer);
            }
            orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("cant save Order" + e.getMessage());
        }
        return order;
    }


    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("invalid order id"));
    }

    @Override
    public LocalDateTime findStartDateOfWork(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("this id is not exist in order table"));
            return order.getStartDayOfWork();

        } catch (Exception e) {
            throw new BadRequestException("cant find start day of work,try again" + e.getMessage());
        }
    }

    @Override
    public List<Order> findAvailableOrdersForSpecialist(Long specialistId) {
        List<Order> ordersBySpecialistSubServicesAndStatus = orderRepository.findOrdersBySpecialistSubServicesAndStatus(specialistId);
        if (ordersBySpecialistSubServicesAndStatus.get(0).getOrderStatus() != OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST ){
            throw new BadRequestException("there is error with find ordersBySpecialistSubServicesAndStatus");
        }
        return ordersBySpecialistSubServicesAndStatus;
    }

    @Override
    public List<Order> findAllByOrderStatusAndSpecialistId(Long specialistId) {
        try {
            return orderRepository.findAllByOrderStatusAndSpecialistId(specialistId);
        } catch (Exception e) {
            throw new NotFoundException("there is no order with WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST and WAITING_FOR_SPECIALIST_SELECTION" + e.getMessage());
        }
    }

    // after saving suggestion, this method will execute from order that save method returned
    @Transactional
    @Override
    public Order changeOrderStatusWaitingForSelection(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("this order id is not found!"));
            order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("there is error with updating orderStatus" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Order changeOrderStatusToComingToYourPlace(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("this order id is not found!"));
            order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("invalid order id,choose an existing id" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Order changeOrderStatusToStartedByCustomer(Long orderId, Long customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new NoResultException("invalid order id or customer Id,choose an existing id"));
        if (!Objects.equals(order.getOrderStatus(), OrderStatus.WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE)) {
            throw new BadRequestException("your previous order status is not <WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE>");
        }
        order.setOrderStatus(OrderStatus.STARTED);

        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order changeOrderStatusToDone(Long orderId, Long customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new NotFoundException("invalid order id or customer Id,choose an existing id"));
        if (!Objects.equals(order.getOrderStatus(), OrderStatus.STARTED)) {
            throw new BadRequestException("your previous order status is not <STARTED>");
        }
        order.setOrderStatus(OrderStatus.DONE);

        return orderRepository.save(order);

    }


//    public void changeStatusOfOrderByCustomerToFinish(Integer suggestionId, String timeOfFinishingWork) {
//        Suggestions suggestion = suggestionRepository.findById(suggestionId)
//                .orElseThrow(() -> new NotFoundException("i can not found this suggestion"));
//
//        String timeOfStartingWork = suggestion.getTimeOfStartingWork();
//        String durationTimeOfWork = suggestion.getDurationTimeOfWork();
//
//        LocalDateTime startWorkTime = LocalDateTime.parse(timeOfStartingWork, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
//        LocalTime durationTime = LocalTime.parse(durationTimeOfWork, DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        LocalDateTime expectedFinishTime = startWorkTime
//                .plusHours(durationTime.getHour())
//                .plusMinutes(durationTime.getMinute())
//                .plusSeconds(durationTime.getSecond());
//
//        LocalDateTime actualFinishTime = LocalDateTime.parse(timeOfFinishingWork, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//        if (actualFinishTime.isAfter(expectedFinishTime)) {
//            long delayHours = ChronoUnit.HOURS.between(expectedFinishTime, actualFinishTime);
//            int ratingReduction = (int) delayHours;
//
//            Specialist specialist = suggestion.getSpecialist();
//            Double currentRating = specialist.getAverageScores();
//            Double updatedRating = Math.max(0, currentRating - ratingReduction);
//
//            if (updatedRating <= 0) {
//                specialist.setSpecialistStatus(SpecialistStatus.NEW);
//                specialistRepository.save(specialist);
//                throw new BadRequestException("your account is disabled");
//            } else {
//                expert.setStars(updatedRating);
//                expertRepository.save(expert);
//            }
//        }
//
//        CustomerOrder customerOrder = suggestion.getCustomerOrder();
//        customerOrder.setStatusOfOrder(StatusOfOrder.DONE);
//        customerOrderRepository.save(customerOrder);
//    }




    // Assume the score to deduct per hour of delay
    private final Double SCORE_TO_DEDUCT_PER_HOUR = 1.0;

    public void checkOrdersAndUpdateScores(Long specialistId) {
        List<Order> startedOrders = orderRepository.findAllByOrderStatus(OrderStatus.STARTED);

        for (Order order : startedOrders) {
            LocalDateTime now = LocalDateTime.now();
            // بررسی کردن زمان شروع کار و مقایسه با زمان کنونی
            if (now.isAfter(order.getStartDayOfWork())) {
                long hoursPassed = Duration.between(order.getStartDayOfWork(), now).toHours();
                Suggestions suggestion = suggestionsRepository.findById(specialistId)
                        .orElseThrow(() -> new NotFoundException("suggestion is not found"));
//                Suggestions suggestion = findRelevantSuggestionForOrder(order); // پیدا کردن ساعت پیشنهادی برای شروع کار
                if (suggestion != null) {
                    long hoursOfWork = Math.round(suggestion.getDurationOfDailyWork());
                    if (hoursPassed > hoursOfWork) {
                        long delayInHours = hoursPassed - hoursOfWork;
                        Specialist specialist = suggestion.getSpecialist();
                        double newScore = specialist.getAverageScores() - (delayInHours * SCORE_TO_DEDUCT_PER_HOUR);
                        if (newScore <= 0) {
                            specialist.setSpecialistStatus(SpecialistStatus.NEW);
                            throw new BadRequestException("specialist averageScore is zero and and specialist is off working");
                        } else {
                            specialist.setAverageScores(newScore);
                            specialistRepository.save(specialist);
                        }
                    }
                }
            }
        }
    }

}
