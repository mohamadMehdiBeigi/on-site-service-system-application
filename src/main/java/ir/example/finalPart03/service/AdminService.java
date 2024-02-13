package ir.example.finalPart03.service;

import ir.example.finalPart03.model.Admin;
import ir.example.finalPart03.model.Customer;
import ir.example.finalPart03.model.Specialist;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdminService {

    Optional<Admin> findByEmail(String email);

    Boolean checkUniqueEmail(String email);

    List<Specialist> findAllSpecialistsByCriteria(Map<String, String > param);

    List<Customer> findAllCustomerByCriteria(Map<String, String > param);

}
