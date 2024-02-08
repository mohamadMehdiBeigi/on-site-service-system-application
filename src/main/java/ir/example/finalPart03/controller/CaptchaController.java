package ir.example.finalPart03.controller;

import ir.example.finalPart03.service.impl.CaptchaService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CaptchaController {

    private CaptchaService captchaService;

    @GetMapping("/captcha")
    public ResponseEntity<String> getCaptcha(HttpSession session) {
        String captcha = captchaService.generateCaptchaToken(session);
        return ResponseEntity.ok(captcha);
    }

    @PostMapping("/verify-captcha")
    public ResponseEntity<String> verifyCaptcha(@RequestBody Map<String, String > body, HttpSession session) {
        boolean valid = captchaService.validateCaptcha(body.get("captchaAnswer"), session);
        return valid ? ResponseEntity.ok("Captcha is valid") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid captcha");
    }
}