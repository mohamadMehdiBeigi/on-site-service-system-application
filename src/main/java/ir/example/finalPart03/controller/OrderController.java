package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.orderDto.OrderRequestDto;
import ir.example.finalPart03.dto.orderDto.OrderResponseDto;
import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private ModelMapper modelMapper;


    @PostMapping("/saveOrder")
    public ResponseEntity<OrderResponseDto> saveOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order orderMapper = modelMapper.map(orderRequestDto, Order.class);

        Order order = orderService.saveOrder(orderMapper, orderMapper.getSubServices().getBasePrice());
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/findOrder{orderId}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/findAvailableOrdersForSpecialist{specialistId}")
    public ResponseEntity<List<OrderResponseDto>> findAvailableOrdersForSpecialist(@PathVariable Long specialistId) {
        List<Order> availableOrdersForSpecialist = orderService.findAvailableOrdersForSpecialist(specialistId);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order order : availableOrdersForSpecialist) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtoList.add(orderResponseDto);
        }
        return ResponseEntity.ok(orderResponseDtoList);
    }

    @GetMapping("/findAllByOrderStatusAndSpecialistId{specialistId}")
    public ResponseEntity<List<OrderResponseDto>> findAllByOrderStatusAndSpecialistId(@PathVariable Long specialistId) {
        List<Order> allByOrderStatusAndSpecialistId = orderService.findAllByOrderStatusAndSpecialistId(specialistId);
        List<OrderResponseDto> orderResponseDtosList = new ArrayList<>();
        for (Order order : allByOrderStatusAndSpecialistId) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtosList.add(orderResponseDto);
        }
        return ResponseEntity.ok(orderResponseDtosList);
    }

    @PutMapping("/changeOrderStatusToComingToYourPlace{orderId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToComingToYourPlace(@PathVariable Long orderId){
        Order order = orderService.changeOrderStatusToComingToYourPlace(orderId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/changeOrderStatusToStarted{orderId}/{customerId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToStartedByCustomer(@PathVariable Long orderId, @PathVariable Long customerId){
        Order order = orderService.changeOrderStatusToStartedByCustomer(orderId, customerId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/changeOrderStatusToDone{orderId}/{customerId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToDone(@PathVariable Long orderId, @PathVariable Long customerId){
        Order order = orderService.changeOrderStatusToDone(orderId, customerId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }





}
