package ir.example.finalPart03.service.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CaptchaService {

    private static final int CAPTCHA_LENGTH = 6;

    public String generateCaptchaToken(HttpSession session) {
        String captcha = generateRandomString(CAPTCHA_LENGTH);
        session.setAttribute("captcha", captcha); // Save captcha in the session
        return captcha;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        while (captcha.length() < length) {
            int index = (int) (random.nextFloat() * characters.length());
            captcha.append(characters.charAt(index));
        }
        return captcha.toString();
    }

    public boolean validateCaptcha(String captchaValue, HttpSession session) {
        String sessionCaptcha = (String) session.getAttribute("captcha");
        return captchaValue.equals(sessionCaptcha);
    }
}