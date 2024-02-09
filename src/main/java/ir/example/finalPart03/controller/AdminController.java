package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.customerDto.CustomerResponseDto;
import ir.example.finalPart03.dto.specialistDto.SpecialistResponseDto;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.Specialist;
import ir.example.finalPart03.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    private ModelMapper modelMapper;


    @PostMapping("/admin/findAllSpecialistsByCriteria")
    //firstname - lastname - email - specialistField(select a serviceName) - averageScoresOrderBy(asc or desc)
    public List<SpecialistResponseDto> findAllSpecialistsByCriteria(@RequestBody Map<String, String> param) {

        List<SpecialistResponseDto> criteriaSearchDtoList = new ArrayList<>();
        List<Specialist> allSpecialistsByCriteria = adminService.findAllSpecialistsByCriteria(param);
        for (Specialist s : allSpecialistsByCriteria
        ) {
            SpecialistResponseDto specialistResponseDto = modelMapper.map(s, SpecialistResponseDto.class);
            criteriaSearchDtoList.add(specialistResponseDto);
        }
        return criteriaSearchDtoList;
    }

    @PostMapping("/admin/findAllCustomerByCriteria")
    public List<CustomerResponseDto> findAllCustomerByCriteria(@RequestBody Map<String, String> param) {
        List<CustomerResponseDto> criteriaSearchDtoList = new ArrayList<>();
        List<Customer> allCustomerByCriteria = adminService.findAllCustomerByCriteria(param);
        for (Customer s : allCustomerByCriteria
        ) {
            CustomerResponseDto customerResponseDto = modelMapper.map(s, CustomerResponseDto.class);
            criteriaSearchDtoList.add(customerResponseDto);
        }
        return criteriaSearchDtoList;
    }
}
