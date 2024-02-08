package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Admin;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.Specialist;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Admin findByEmailAndPassword(String email, String password);

    Boolean checkUniqueEmail(String email);

    List<Specialist> findAllSpecialistsByCriteria(Map<String, String > param);

    List<Customer> findAllCustomerByCriteria(String firstname, String lastname, String email);
}
