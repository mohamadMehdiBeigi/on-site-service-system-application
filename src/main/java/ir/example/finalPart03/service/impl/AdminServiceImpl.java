package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.repository.AdminRepository;
import ir.example.finalPart03.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Boolean checkUniqueEmail(String email) {
        Integer checked = adminRepository.checkUniqueEmail(email);
        if (checked != 0) {
            throw new BadRequestException("this email is you entered is already exist.try another email");
        }
        return true;
    }

    public List<Specialist> findAllSpecialistsByCriteria(Map<String, String> param) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Specialist> criteriaQuery = criteriaBuilder.createQuery(Specialist.class);
        Root<Specialist> specialistRoot = criteriaQuery.from(Specialist.class);

        List<Predicate> predicates = new ArrayList<>();
        ArrayList<Order> orderList = new ArrayList<>();


        if (param.containsKey("firstname") && param.get("firstname") != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("firstname"), "%" + param.get("firstname") + "%"));
        }

        if (param.containsKey("lastname") && param.get("lastname") != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("lastname"), "%" + param.get("lastname") + "%"));
        }

        if (param.containsKey("email") && param.get("email") != null) {
            predicates.add(criteriaBuilder.equal(specialistRoot.get("email"), param.get("email")));
        }

        if (param.containsKey("specialistField") && param.get("specialistField") != null) {

            Join<Specialist, SubServices> subServiceJoin = specialistRoot.join("subServices", JoinType.INNER);
            Join<SubServices, Services> serviceJoin = subServiceJoin.join("services", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(serviceJoin.get("serviceName"), param.get("specialistField")));
        }

        if (param.containsKey("averageScoresOrderBy") && param.get("averageScoresOrderBy") != null) {
            if (param.get("averageScoresOrderBy").equalsIgnoreCase("asc")) {
                orderList.add(criteriaBuilder.asc(specialistRoot.get("averageScores")));
            } else if (param.get("averageScoresOrderBy").equalsIgnoreCase("desc")) {
                orderList.add(criteriaBuilder.desc(specialistRoot.get("averageScores")));
            }
        }

        if (orderList.isEmpty()) {
            criteriaQuery.select(specialistRoot)
                    .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                    .orderBy(criteriaBuilder.asc(specialistRoot.get("id")));
        } else {
            criteriaQuery.select(specialistRoot)
                    .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                    .orderBy(orderList.toArray(new Order[0]));
        }
        TypedQuery<Specialist> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }





    @Override
    public List<Customer> findAllCustomerByCriteria(Map<String, String> param) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> specialistRoot = criteriaQuery.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();


        if (param.containsKey("firstname") && param.get("firstname") != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("firstname"), "%" + param.get("firstname") + "%"));
        }

        if (param.containsKey("lastname") && param.get("lastname") != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("lastname"), "%" + param.get("lastname") + "%"));
        }

        if (param.containsKey("email") && param.get("email") != null) {
            predicates.add(criteriaBuilder.equal(specialistRoot.get("email"), param.get("email")));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();

    }

}
