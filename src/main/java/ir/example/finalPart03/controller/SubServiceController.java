package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.subServiceDto.SubServiceRequestDto;
import ir.example.finalPart03.dto.subServiceDto.SubServiceResponseDto;
import ir.example.finalPart03.model.SubServices;
import ir.example.finalPart03.service.SubServiceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subServices")
@AllArgsConstructor
public class SubServiceController {

    private SubServiceService subServiceService;

    private ModelMapper modelMapper;

    @PostMapping("/save")
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

    @GetMapping("/findAllByServiceId/{serviceId}")
    public ResponseEntity<List<SubServiceResponseDto>> findAllByServiceId(@PathVariable Long serviceId) {
        List<SubServices> subServiceServiceAllByServiceId = subServiceService.findAllByServiceId(serviceId);
        List<SubServiceResponseDto> subServiceResponseDtoList = new ArrayList<>();
        for (SubServices subServices : subServiceServiceAllByServiceId
        ) {
            SubServiceResponseDto subServiceResponseDto = modelMapper.map(subServices, SubServiceResponseDto.class);
            subServiceResponseDtoList.add(subServiceResponseDto);
        }
        return ResponseEntity.ok(subServiceResponseDtoList);
    }

    @DeleteMapping("/deleteSubServiceById/{subServiceId}")
    public ResponseEntity<Void> deleteSubServiceById(@PathVariable Long subServiceId){
        subServiceService.deleteSubServiceById(subServiceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/removeSubServiceByServiceId/{serviceId}")
    public ResponseEntity<Void> removeSubServiceByServiceId(@PathVariable Long serviceId){
        subServiceService.removeSubServiceByServiceId(serviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteSubServicesBySpecialistId/{specialistId}")
    public ResponseEntity<Void> deleteSubServicesBySpecialistId(@PathVariable Long specialistId){
        subServiceService.deleteSubServicesOfSpecialist(specialistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
