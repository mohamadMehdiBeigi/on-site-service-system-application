package ir.example.finalPart03.controller;

import ir.example.finalPart03.config.exceptions.BadRequestException;
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
        try {
            Specialist specialist = modelMapper.map(specialistRequestDto, Specialist.class);
            Specialist saveSpecialist = specialistService.saveSpecialist(specialist);
            SpecialistResponseDto specialistResponseDto = modelMapper.map(saveSpecialist, SpecialistResponseDto.class);
            return new ResponseEntity<>(specialistResponseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("cant save specialist" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/specialist/{email}/{password}")
    public ResponseEntity<SpecialistResponseDto> findByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        try {
            Specialist specialist = specialistService.findByEmailAndPassword(email, password);
            SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
            return new ResponseEntity<>(specialistResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println("cant find data with this special id" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/specialist/confirmingSpecialStatus/{specialistId}")
    public ResponseEntity<Void> confirmingSpecialStatus(@PathVariable Long specialistId) {
        if (specialistService.checkingSpecialistStatus(specialistId)) {
            throw new BadRequestException("this account is already CONFIRMED");
        }
        try {
            specialistService.confirmingSpecialStatus(specialistId);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            System.err.println("cant change specialistStatus" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/specialist/saveImageToFile/{specialistId}")
    public ResponseEntity<String> saveImageToFile(@PathVariable Long specialistId) {
        try {
            specialistService.saveImageToFile(specialistId);
            return ResponseEntity.ok("successfully adding" + specialistId + "image to 'src\\main\\resources\\extractedImage'");

        } catch (Exception e) {
            System.err.println("this id is not existed, cant save image" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/specialist/changePassword{customerId}/{password}/{confirmingPassword}")
    public ResponseEntity<SpecialistResponseDto> changePassword(@PathVariable Long customerId, @PathVariable String password, @PathVariable String confirmingPassword) {
        try {
            Specialist specialist = specialistService.changePassword(customerId, password, confirmingPassword);
            SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
            return ResponseEntity.ok(specialistResponseDto);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/specialist/addSubServiceToSpecialist/{specialistId}/{subServiceId}")
    public ResponseEntity<Void> addSubServiceToSpecialist(@PathVariable Long specialistId, @PathVariable Long subServiceId) {
        try {
            specialistService.addSubServiceToSpecialist(specialistId, subServiceId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException("invalid specialist id or subServiceId, cant add..." + e.getMessage());
        }
    }

    @PutMapping("/specialist/seeAverageScore/{specialistId}")
    public ResponseEntity<SpecialistResponseDto> seeAverageScore(@PathVariable Long specialistId) {
        try {
            Specialist specialist = specialistService.seeAverageScore(specialistId);
            SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
            return new ResponseEntity<>(specialistResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException("invalid specialist id for" + e.getMessage());
        }
    }
}
