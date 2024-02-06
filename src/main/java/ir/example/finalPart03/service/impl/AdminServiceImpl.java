package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.Admin;
import ir.example.finalPart03.repository.AdminRepository;
import ir.example.finalPart03.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Admin findByEmailAndPassword(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new NotFoundException("this email is not found!"));
    }

    @Override
    public Boolean checkUniqueEmail(String email) {
        Integer checked = adminRepository.checkUniqueEmail(email);
        if (checked != 0) {
            throw new BadRequestException("this email is you entered is already exist.try another email");
        }
        return true;
    }
}
