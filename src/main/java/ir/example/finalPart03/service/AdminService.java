package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Admin;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdminService {

    Optional<Admin> findByEmail(String email);

    Boolean checkUniqueEmail(String email);

    List findAllUsersByCriteriaFinal(Class<?> userType, Map<String, String> param);
}
