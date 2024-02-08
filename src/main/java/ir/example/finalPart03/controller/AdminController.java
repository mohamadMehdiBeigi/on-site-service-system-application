package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.criteriaSearchDto.CriteriaSearchDto;
import ir.example.finalPart03.model.Admin;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    private ModelMapper modelMapper;


    @GetMapping("/login/admin/{email}/{password}")
    public ResponseEntity<Admin> findByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        return ResponseEntity.accepted().body(adminService.findByEmailAndPassword(email, password));
    }

    @PostMapping("/admin/findAllSpecialistsByCriteria")
    //firstname - lastname - email - specialistField(select a serviceName) - averageScoresOrderBy(asc or desc)
    public List<CriteriaSearchDto> findAllSpecialistsByCriteria(@RequestBody Map<String, String> param) {

        List<CriteriaSearchDto> criteriaSearchDtoList = new ArrayList<>();
        List<Specialist> allSpecialistsByCriteria = adminService.findAllSpecialistsByCriteria(param);
        for (Specialist s : allSpecialistsByCriteria
        ) {
            CriteriaSearchDto criteriaSearchDto = modelMapper.map(s, CriteriaSearchDto.class);
            criteriaSearchDtoList.add(criteriaSearchDto);
        }
        return criteriaSearchDtoList;
    }
}
