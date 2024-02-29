package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.orderDto.OrderResponseDto;
import ir.example.finalPart03.dto.serviceDto.ServiceRequestDto;
import ir.example.finalPart03.dto.serviceDto.ServiceResponseDto;
import ir.example.finalPart03.dto.specialistDto.SpecialistResponseDto;
import ir.example.finalPart03.dto.subServiceDto.SubServiceRequestDto;
import ir.example.finalPart03.dto.subServiceDto.SubServiceResponseDto;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final ModelMapper modelMapper;

    private final ServicesService servicesService;

    private final SubServiceService subServiceService;

    private final SpecialistService specialistService;

    private final OrderService orderService;


    @PostMapping("/saveService")
    public ResponseEntity<ServiceResponseDto> saveService(@RequestBody ServiceRequestDto serviceRequestDto) {
        Services services = modelMapper.map(serviceRequestDto, Services.class);
        Services savedService = servicesService.saveService(services);
        ServiceResponseDto serviceResponseDto = modelMapper.map(savedService, ServiceResponseDto.class);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteService/{serviceId}")
    public ResponseEntity<Void> deleteServiceById(@PathVariable Long serviceId) {
        servicesService.deleteServiceById(serviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteSubService/{subServiceId}")
    public ResponseEntity<Void> deleteSubService(@PathVariable Long subServiceId) {
        subServiceService.deleteSubService(subServiceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteSpecialist/{specialistId}")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long specialistId) {
        specialistService.deleteSpecialist(specialistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/saveSubService")
    public ResponseEntity<SubServiceResponseDto> saveSubService(@RequestBody SubServiceRequestDto serviceRequestDto) {
        SubServices subServices = modelMapper.map(serviceRequestDto, SubServices.class);
        SubServices saveSubService = subServiceService.saveSubService(subServices, serviceRequestDto.getServiceId());
        SubServiceResponseDto subServiceResponseDto = modelMapper.map(saveSubService, SubServiceResponseDto.class);
        return new ResponseEntity<>(subServiceResponseDto, HttpStatus.CREATED);

    }

    @PutMapping("/updateBasePriceAndDescription/{subServiceId}/{newBasePrice}/{description}")
    public ResponseEntity<SubServiceResponseDto> updateBasePriceAndDescription(@PathVariable Long subServiceId, @PathVariable Double newBasePrice, @PathVariable String description) {
        SubServices subServices = subServiceService.updateBasePriceAndDescription(subServiceId, newBasePrice, description);
        return ResponseEntity.ok(modelMapper.map(subServices, SubServiceResponseDto.class));
    }


    @PutMapping("/confirmingSpecialStatus/{specialistId}")
    public ResponseEntity<Void> confirmingSpecialStatus(@PathVariable Long specialistId) {
        specialistService.confirmingSpecialStatus(specialistId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAllServices")
    public ResponseEntity<List<Services>> findAllServices() {
        return new ResponseEntity<>(servicesService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/findAllUsersByCriteria/{userType}")
    public ResponseEntity<List<SpecialistResponseDto>> findAllUsersByCriteria(@PathVariable String userType, @RequestBody Map<String, String> param) {
        Class<?> user = null;
        if (userType.equals("Specialist")) {
            user = Specialist.class;
        } else if (userType.equals("Customer")) {
            user = Customer.class;
        }
        List<SpecialistResponseDto> criteriaSearchDtoList = new ArrayList<>();
        List allUsersByCriteriaFinal = adminService.findAllUsersByCriteriaFinal(user, param);
        for (Object allUsers : allUsersByCriteriaFinal) {
            SpecialistResponseDto specialistResponseDto = modelMapper.map(allUsers, SpecialistResponseDto.class);
            criteriaSearchDtoList.add(specialistResponseDto);
        }
        return new ResponseEntity<>(criteriaSearchDtoList, HttpStatus.OK);
    }

    @PostMapping("/getUserOrderHistory/{userType}/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getUserOrderHistory(@PathVariable String userType, @PathVariable Long userId, @RequestBody Map<String, String> param) {
        Class<?> user = null;
        if (userType.equalsIgnoreCase("Specialist")) {
            user = Specialist.class;
        } else if (userType.equalsIgnoreCase("Customer")) {
            user = Customer.class;
        }
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<Order> userOrderHistory = orderService.getUserOrderHistory(userId, user, param);
        for (Order order : userOrderHistory) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtoList.add(orderResponseDto);
        }
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/countOrdersByCriteria/{userType}/{entityId}")
    public ResponseEntity<Long> countOrdersByCriteria(@PathVariable String userType, @PathVariable Long entityId) {
        Class<?> user = null;
        if (userType.equalsIgnoreCase("Specialist")) {
            user = Specialist.class;
        } else if (userType.equalsIgnoreCase("Customer")) {
            user = Customer.class;
        }
        return new ResponseEntity<>(orderService.countOrdersByCriteria(entityId, user), HttpStatus.OK);
    }



}
