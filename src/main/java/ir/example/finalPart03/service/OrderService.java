package ir.example.finalPart03.service;


import ir.example.finalPart03.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Order saveOrder(Order order, Double basePrice, Long subServicesId, Long customerId);

    Order findById(Long id);

    LocalDateTime findStartDateOfWork(Long orderId);

    List<Order> findAllByOrderStatusAndSpecialistId(Long specialistId);

    List<Order> findAvailableOrdersForSpecialist(Long specialistId);

    Order changeOrderStatusWaitingForSelection(Long orderId);

    Order changeOrderStatusToComingToYourPlace(Long suggestionId);

    Order changeOrderStatusToStartedByCustomer(Long orderId, Long customerId);

    Order changeOrderStatusToDone(Long orderId, Long customerId);

    Order changeOrderStatusToPaid(Long orderId);

    void checkOrdersAndUpdateScores(Long suggestionId, Long specialistId);

    List<Order> getUserOrderHistory(Long userId, Class<?> userType, Map<String, String> param);

    Long countOrdersByCriteria(Long entityId, Class<?> userType);

    List<Order> findAllOrdersByCustomerIdAndOrderStatus(Long customerId, String orderStatus);

    List<Order> findAllOrderBySpecialistIdAndOrderStatus(Long specialistId, String orderStatus);
}
