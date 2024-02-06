package ir.example.finalPart03.service;


import ir.example.finalPart03.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order saveOrder(Order order, Double basePrice);

    Order findById(Long id);

    LocalDateTime findStartDateOfWork(Long orderId);

    List<Order> findAllByOrderStatusAndSpecialistId(Long specialistId);

    List<Order> findAvailableOrdersForSpecialist(Long specialistId);

    Order changeOrderStatusWaitingForSelection(Long orderId);

    Order changeOrderStatusToComingToYourPlace(Long orderId);

    Order changeOrderStatusToStartedByCustomer(Long orderId, Long customerId);

    Order changeOrderStatusToDone(Long orderId, Long customerId);
}
