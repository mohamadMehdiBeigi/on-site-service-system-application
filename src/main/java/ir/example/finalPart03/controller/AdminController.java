package ir.example.finalPart03.controller;

import ir.example.finalPart03.model.Admin;
import ir.example.finalPart03.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @GetMapping("/login/admin/{email}/{password}")
    public ResponseEntity<Admin> findByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
        return ResponseEntity.accepted().body(adminService.findByEmailAndPassword(email, password));
    }
}
