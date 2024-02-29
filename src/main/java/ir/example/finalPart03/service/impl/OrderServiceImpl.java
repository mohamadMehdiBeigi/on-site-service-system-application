package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.model.enums.OrderStatus;
import ir.example.finalPart03.model.enums.SpecialistStatus;
import ir.example.finalPart03.repository.OrderRepository;
import ir.example.finalPart03.repository.SpecialistRepository;
import ir.example.finalPart03.repository.SuggestionsRepository;
import ir.example.finalPart03.service.OrderService;
import ir.example.finalPart03.service.SpecialistService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final SpecialistRepository specialistRepository;

    private final SpecialistService specialistService;

    private final SuggestionsRepository suggestionsRepository;


    @Override
    public Order saveOrder(Order order, Double basePrice, Long subServicesId, Long customerId) {
        if (order.getSuggestedPrice() < basePrice) {
            throw new BadRequestException("suggested price is less than base price ");
        } else if (order.getStartDayOfWork().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("your start day of work is before current date and time");
        } else if (order.getOrderStatus() == null && subServicesId != null) {
            SubServices subServices = new SubServices();
            subServices.setId(subServicesId);
            order.setSubServices(subServices);
        } else if (order.getOrderStatus() == null && customerId != null) {
            Customer customer = new Customer();
            customer.setId(customerId);
            order.setCustomer(customer);
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("this id is not exist in order table"));
        try {
            return order.getStartDayOfWork();

        } catch (Exception e) {
            throw new BadRequestException("cant find start day of work,try again" + e.getMessage());
        }
    }

    @Override
    public List<Order> findAvailableOrdersForSpecialist(Long specialistId) {
        List<Order> ordersBySpecialistSubServicesAndStatus = orderRepository.findOrdersBySpecialistSubServicesAndStatus(specialistId);
        if (ordersBySpecialistSubServicesAndStatus.get(0).getOrderStatus() != OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST) {
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("this order id is not found!"));
        order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("there is error with updating orderStatus to<WaitingForSelection>" + e.getMessage());
        }
    }

    @Override
    public Order changeOrderStatusToComingToYourPlace(Long suggestionId) {
        Suggestions suggestions = suggestionsRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("this suggestion id is not found"));
        Long orderId = suggestions.getOrder().getId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("this order id is not found!"));
        order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_TO_COME_TO_YOUR_PLACE);
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("invalid id for change orderStatus to <ComingToYourPlace> for order table" + e.getMessage());
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
        try {
            return orderRepository.save(order);

        } catch (Exception e) {
            throw new BadRequestException("invalid input for change orderStatus to <STARTED> for order table" + e.getMessage());
        }
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
        order.setEndTimeOfWork(LocalDateTime.now());
        try {
            return orderRepository.save(order);

        } catch (Exception e) {
            throw new BadRequestException("invalid id input for change orderStatus to <DONE> for order table" + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Order changeOrderStatusToPaid(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("this order id is not found!"));
        order.setOrderStatus(OrderStatus.PAID);
        try {
            return orderRepository.save(order);

        } catch (Exception e) {
            throw new BadRequestException("invalid id input for change orderStatus to <PAID> for order table" + e.getMessage());
        }
    }


    @Override
    public void checkOrdersAndUpdateScores(Long suggestionId, Long specialistId) {

        Suggestions suggestion = suggestionsRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("suggestion is not found"));
        if (suggestion.getOrder() != null && suggestion.getOrder().getOrderStatus() != OrderStatus.DONE) {
            throw new NotFoundException("order status with this id is not DONE yet");
        }
        Order order = orderRepository.findById(suggestion.getOrder().getId()).orElseThrow(RuntimeException::new);
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(order.getStartDayOfWork())) {
            long hoursPassed = Duration.between(suggestion.getSuggestedTimeToStartWork(), now).toHours();


            long hoursOfWork = Math.round(suggestion.getDurationOfDailyWork());
            if (hoursPassed > hoursOfWork) {
                long delayInHours = hoursPassed - hoursOfWork;
                Specialist specialist = specialistService.findById(specialistId);
                double newScore = specialist.getAverageScores() - delayInHours;
                if (newScore <= 0) {
                    specialist.setSpecialistStatus(SpecialistStatus.NEW);
                    specialist.setAverageScores(0.0);
                    specialistRepository.save(specialist);
                } else {
                    specialist.setAverageScores(newScore);
                    specialistRepository.save(specialist);
                }
            }
        }
    }


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Order> getUserOrderHistory(Long userId, Class<?> userType, Map<String, String> param) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        Path<Customer> userJoin = orderRoot.get("customer");
        Join<Order, Users> specialistJoin = orderRoot.join("subServices", JoinType.LEFT).join("specialists", JoinType.LEFT);

        query.select(orderRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (userType != null && Customer.class.isAssignableFrom(userType)) {
            predicates.add(cb.equal(userJoin.get("id"), userId));
        } else if (userType != null && Specialist.class.isAssignableFrom(userType)) {
            predicates.add(cb.equal(specialistJoin.get("id"), userId));
        }

        if (param.containsKey("startDate") &&
                param.containsKey("endDate") &&
                param.get("startDate") != null &&
                param.get("endDate") != null) {
            LocalDateTime startDate = LocalDateTime.parse(param.get("startDate"));
            LocalDateTime endDate = LocalDateTime.parse(param.get("endDate"));
            predicates.add(cb.between(orderRoot.get("startDayOfWork"), startDate, endDate));
        }
        if (param.containsKey("status") && param.get("status") != null) {
            OrderStatus status = OrderStatus.valueOf(param.get("status"));
            predicates.add(cb.equal(orderRoot.get("orderStatus"), status));
        }
        if (param.containsKey("serviceName") && param.get("serviceName") != null) {
            predicates.add(cb.equal(orderRoot.get("subServices").get("services").get("serviceName"), param.get("serviceName")));
        }
        if (param.containsKey("subServiceName") && param.get("subServiceName") != null) {
            predicates.add(cb.equal(orderRoot.get("subServices").get("subServiceName"), param.get("subServiceName")));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public Long countOrdersByCriteria(Long entityId, Class<?> userType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Order, Users> specialistJoin = orderRoot.join("subServices", JoinType.LEFT).join("specialists", JoinType.LEFT);


        Predicate typePredicate;
        if (Customer.class.isAssignableFrom(userType)) {
            criteriaQuery.select(criteriaBuilder.count(orderRoot));
            typePredicate = criteriaBuilder.equal(orderRoot.get("customer").get("id"), entityId);
            criteriaQuery.where(typePredicate);

            return entityManager.createQuery(criteriaQuery).getSingleResult();

        } else if (Specialist.class.isAssignableFrom(userType)) {
            criteriaQuery.select(criteriaBuilder.count(specialistJoin));
            typePredicate = criteriaBuilder.equal(specialistJoin.get("id"), entityId);
            Predicate statusPredicate = criteriaBuilder.equal(orderRoot.get("orderStatus"), OrderStatus.DONE);

            criteriaQuery.where(typePredicate, statusPredicate);

            return entityManager.createQuery(criteriaQuery).getSingleResult();

        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }

    @Override
    public List<Order> findAllOrdersByCustomerIdAndOrderStatus(Long customerId, String orderStatus) {
        try {
            OrderStatus byTitle = OrderStatus.findByTitle(orderStatus);

            return orderRepository.findAllByCustomerIdAndOrderStatus(customerId, byTitle);

        } catch (Exception e) {
            throw new BadRequestException("there is no orders data with this customerId");
        }
    }

    @Override
    public List<Order> findAllOrderBySpecialistIdAndOrderStatus(Long specialistId, String orderStatus) {
        try {
            OrderStatus byTitle = OrderStatus.findByTitle(orderStatus);

            return orderRepository.findAllBySpecialistIdAndOrderStatus(specialistId, byTitle);

        } catch (Exception e) {
            throw new BadRequestException("there is no orders data with this specialistId");
        }
    }



}
