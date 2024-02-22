package ir.example.finalPart03.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
@AllArgsConstructor
public class ServiceController {

    private ModelMapper modelMapper;


}
