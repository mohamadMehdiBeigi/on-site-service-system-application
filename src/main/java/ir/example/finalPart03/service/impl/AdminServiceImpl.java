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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("rawtypes")
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


    private <T> List<T> findAllUsersByCriteria(Class<T> userType, Map<String, String> param) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(userType);
        Root<T> userRoot = criteriaQuery.from(userType);

        List<Predicate> predicates = new ArrayList<>();
        ArrayList<Order> orderList = new ArrayList<>();

        if (param.containsKey("firstname") && param.get("firstname") != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("firstname"), "%" + param.get("firstname") + "%"));
        }

        if (param.containsKey("lastname") && param.get("lastname") != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("lastname"), "%" + param.get("lastname") + "%"));
        }

        if (param.containsKey("email") && param.get("email") != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("email"), "%" + param.get("email") + "%"));
        }

        if (Specialist.class.isAssignableFrom(userType)) {
            if (param.containsKey("specialistField") && param.get("specialistField") != null) {
                Join<T, SubServices> subServiceJoin = userRoot.join("subServices", JoinType.INNER);
                Join<SubServices, Services> serviceJoin = subServiceJoin.join("services", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(serviceJoin.get("serviceName"), param.get("specialistField")));
            }

            if (param.containsKey("maxOrMinAverageScores") && param.get("maxOrMinAverageScores") != null) {
                Subquery<Double> averageScoreSubquery = criteriaQuery.subquery(Double.class);
                Root<Specialist> localRoot = averageScoreSubquery.from(Specialist.class);

                if (param.get("maxOrMinAverageScores").equalsIgnoreCase("max")) {
                    averageScoreSubquery.select(criteriaBuilder.max(localRoot.get("averageScores")));
                    predicates.add(criteriaBuilder.equal(userRoot.get("averageScores"), averageScoreSubquery));

                } else if (param.get("maxOrMinAverageScores").equalsIgnoreCase("min")) {
                    averageScoreSubquery.select(criteriaBuilder.min(localRoot.get("averageScores")));
                    predicates.add(criteriaBuilder.equal(userRoot.get("averageScores"), averageScoreSubquery));

                }
            }
        }

        if (Specialist.class.isAssignableFrom(userType) && param.containsKey("averageScoresOrderBy") && param.get("averageScoresOrderBy") != null) {
            if (param.get("averageScoresOrderBy").equalsIgnoreCase("asc")) {
                orderList.add(criteriaBuilder.asc(userRoot.get("averageScores")));
            } else if (param.get("averageScoresOrderBy").equalsIgnoreCase("desc")) {
                orderList.add(criteriaBuilder.desc(userRoot.get("averageScores")));
            }
        }

        if (param.containsKey("registrationStartDate") &&
                param.containsKey("registrationEndDate") &&
                param.get("registrationStartDate") != null && param.get("registrationEndDate") != null){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime registrationStartDate = LocalDateTime.parse(param.get("registrationStartDate"), formatter);
            LocalDateTime registrationEndDate = LocalDateTime.parse(param.get("registrationEndDate"), formatter);
            predicates.add(criteriaBuilder.between(userRoot.get("signupDate"), registrationStartDate, registrationEndDate));

        }


            if (!orderList.isEmpty()) {
            criteriaQuery.orderBy(orderList.toArray(new Order[0]));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public List findAllUsersByCriteriaFinal(Class<?> userType, Map<String, String> param) {
        if (userType == Customer.class) {
            return findAllUsersByCriteria(Customer.class, param);
        } else if (userType == Specialist.class) {
            return findAllUsersByCriteria(Specialist.class, param);
        }
        return null;
    }

}
