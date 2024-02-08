package ir.example.finalPart03.service.impl;

import ir.example.finalPart03.config.exceptions.BadRequestException;
import ir.example.finalPart03.config.exceptions.NotFoundException;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.repository.AdminRepository;
import ir.example.finalPart03.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    @PersistenceContext
    private EntityManager entityManager;


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
            // Join برای رسیدن به نام سرویس در entity Services
            Join<Specialist, SubServices> subServiceJoin = specialistRoot.join("subServices", JoinType.INNER);
            Join<SubServices, Services> serviceJoin = subServiceJoin.join("services", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(serviceJoin.get("serviceName"), param.get("specialistField")));
        }

// تعیین ترتیب مرتب‌سازی بر اساس averageScores
        if (param.containsKey("averageScoresOrderBy") && param.get("averageScoresOrderBy") != null) {
            if (param.get("averageScoresOrderBy").equalsIgnoreCase("asc")) {
                orderList.add(criteriaBuilder.asc(specialistRoot.get("averageScores")));
            } else if (param.get("averageScoresOrderBy").equalsIgnoreCase("desc")) {
                orderList.add(criteriaBuilder.desc(specialistRoot.get("averageScores")));
            }
        }

// بر اساس تمام شرط‌های جمع‌آوری شده کوئری را می‌سازیم
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




//    @Override
//    public List<Specialist> findAllSpecialistsByCriteria(String firstname, String lastname, String email, String averageScoresOrderBy, String specialistField, CriteriaSearchDto criteriaSearchDto) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Specialist> criteriaQuery = criteriaBuilder.createQuery(Specialist.class);
//        Root<Specialist> specialistRoot = criteriaQuery.from(Specialist.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//        ArrayList<Order> orderList = new ArrayList<>();
//
//
//        if (criteriaSearchDto.getFirstname() != null) {
//            predicates.add(criteriaBuilder.like(specialistRoot.get("firstname"), "%" + firstname + "%"));
//        }
//
//        if (lastname != null) {
//            predicates.add(criteriaBuilder.like(specialistRoot.get("lastname"), "%" + lastname + "%"));
//        }
//
//        if (email != null) {
//            predicates.add(criteriaBuilder.equal(specialistRoot.get("email"), email));
//        }
//
//        if (specialistField != null) {
//            // Join برای رسیدن به نام سرویس در entity Services
//            Join<Specialist, SubServices> subServiceJoin = specialistRoot.join("subServices", JoinType.INNER);
//            Join<SubServices, Services> serviceJoin = subServiceJoin.join("services", JoinType.INNER);
//            predicates.add(criteriaBuilder.equal(serviceJoin.get("serviceName"), specialistField));
//        }
//
//// تعیین ترتیب مرتب‌سازی بر اساس averageScores
//        if (averageScoresOrderBy != null) {
//            if (averageScoresOrderBy.equalsIgnoreCase("asc")) {
//                orderList.add(criteriaBuilder.asc(specialistRoot.get("averageScores")));
//            } else if (averageScoresOrderBy.equalsIgnoreCase("desc")) {
//                orderList.add(criteriaBuilder.desc(specialistRoot.get("averageScores")));
//            }
//        }
//
//// بر اساس تمام شرط‌های جمع‌آوری شده کوئری را می‌سازیم
//        if (orderList.isEmpty()) {
//            criteriaQuery.select(specialistRoot)
//                    .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
//                    .orderBy(criteriaBuilder.asc(specialistRoot.get("id")));
//        } else {
//            criteriaQuery.select(specialistRoot)
//                    .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
//                    .orderBy(orderList.toArray(new Order[0]));
//        }
//        TypedQuery<Specialist> query = entityManager.createQuery(criteriaQuery);
//
//        return query.getResultList();
//    }


    @Override
    public List<Customer> findAllCustomerByCriteria(String firstname, String lastname, String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> specialistRoot = criteriaQuery.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();


        if (firstname != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("firstname"), "%" + firstname + "%"));
        }

        if (lastname != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("lastname"), "%" + lastname + "%"));
        }

        if (email != null) {
            predicates.add(criteriaBuilder.equal(specialistRoot.get("email"), email));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();

    }

}
