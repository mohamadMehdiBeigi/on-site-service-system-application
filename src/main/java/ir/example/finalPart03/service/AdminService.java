package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Admin;

public interface AdminService {
    Admin findByEmailAndPassword(String email, String password);

    Boolean checkUniqueEmail(String email);
}
