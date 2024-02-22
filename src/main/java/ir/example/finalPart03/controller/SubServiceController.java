package ir.example.finalPart03.controller;

import ir.example.finalPart03.service.SubServiceService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subServices")
@AllArgsConstructor
public class SubServiceController {

    private SubServiceService subServiceService;

    private ModelMapper modelMapper;




//    @DeleteMapping("/deleteSubServiceById/{subServiceId}")
//    public ResponseEntity<Void> deleteSubServiceById(@PathVariable Long subServiceId){
//        subServiceService.deleteSubService(subServiceId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping("/removeSubServiceByServiceId/{serviceId}")
//    public ResponseEntity<Void> removeSubServiceByServiceId(@PathVariable Long serviceId){
//        subServiceService.removeSubServiceByServiceId(serviceId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping("/deleteSubServicesBySpecialistId/{specialistId}")
//    public ResponseEntity<Void> deleteSubServicesBySpecialistId(@PathVariable Long specialistId){
//        subServiceService.deleteSubServicesOfSpecialist(specialistId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
