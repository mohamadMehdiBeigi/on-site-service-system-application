package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.serviceDto.ServiceRequestDto;
import ir.example.finalPart03.dto.serviceDto.ServiceResponseDto;
import ir.example.finalPart03.model.Services;
import ir.example.finalPart03.service.ServicesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@AllArgsConstructor
public class ServiceController {

    private ServicesService servicesService;
    private ModelMapper modelMapper;

    @PostMapping("/saveService")
    public ResponseEntity<ServiceResponseDto> saveService(@RequestBody ServiceRequestDto serviceRequestDto){
        Services services = modelMapper.map(serviceRequestDto, Services.class);
        Services savedService = servicesService.saveService(services);
        ServiceResponseDto serviceResponseDto = modelMapper.map(savedService, ServiceResponseDto.class);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteService/{serviceId}")
    public ResponseEntity<Void> deleteServiceById(@PathVariable Long serviceId){
        servicesService.deleteServiceById(serviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
