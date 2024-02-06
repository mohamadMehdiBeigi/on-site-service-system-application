package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.service.OrderService;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Order saveOrder(Order order, Double basePrice) {
        if (order.getSuggestedPrice() < basePrice) {
            throw new BadRequestException("suggested price is less than base price ");
        }
        if (order.getStartDayOfWork().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("your start day of work is before current date and time");
        }
        try {
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
}
