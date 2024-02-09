package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.specialistDto.SpecialistRequestDto;
import ir.example.finalPart03.dto.specialistDto.SpecialistResponseDto;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.service.SpecialistService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SpecialistController {

    private SpecialistService specialistService;

    private ModelMapper modelMapper;

    @PostMapping("/specialist/save")
    public ResponseEntity<SpecialistResponseDto> saveSpecialist(@RequestBody SpecialistRequestDto specialistRequestDto) {
        Specialist specialist = modelMapper.map(specialistRequestDto, Specialist.class);
        Specialist saveSpecialist = specialistService.saveSpecialist(specialist);
        SpecialistResponseDto specialistResponseDto = modelMapper.map(saveSpecialist, SpecialistResponseDto.class);
        return new ResponseEntity<>(specialistResponseDto, HttpStatus.CREATED);
    }


    @PutMapping("/specialist/confirmingSpecialStatus/{specialistId}")
    public ResponseEntity<Void> confirmingSpecialStatus(@PathVariable Long specialistId) {
        specialistService.confirmingSpecialStatus(specialistId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/specialist/saveImageToFile/{specialistId}")
    public ResponseEntity<String> saveImageToFile(@PathVariable Long specialistId) {
        specialistService.saveImageToFile(specialistId);
        return ResponseEntity.ok("successfully adding" + specialistId + "image to 'src\\main\\resources\\extractedImage'");
    }

    @PutMapping("/specialist/changePassword/{specialistId}/{password}/{confirmingPassword}")
    public ResponseEntity<SpecialistResponseDto> changePassword(@PathVariable Long specialistId, @PathVariable String password, @PathVariable String confirmingPassword) {
        Specialist specialist = specialistService.changePassword(specialistId, password, confirmingPassword);
        SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
        return ResponseEntity.ok(specialistResponseDto);
    }

    @PutMapping("/specialist/addSubServiceToSpecialist/{specialistId}/{subServiceId}")
    public ResponseEntity<Void> addSubServiceToSpecialist(@PathVariable Long specialistId, @PathVariable Long subServiceId) {
        specialistService.addSubServiceToSpecialist(specialistId, subServiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/specialist/seeAverageScore/{specialistId}")
    public ResponseEntity<SpecialistResponseDto> seeAverageScore(@PathVariable Long specialistId) {
        Specialist specialist = specialistService.seeAverageScore(specialistId);
        SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
        return new ResponseEntity<>(specialistResponseDto, HttpStatus.OK);
    }
}
