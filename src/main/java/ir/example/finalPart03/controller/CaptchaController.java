package ir.example.finalPart03.controller;

import ir.example.finalPart03.service.impl.CaptchaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CaptchaController {

    private CaptchaService captchaService;


}