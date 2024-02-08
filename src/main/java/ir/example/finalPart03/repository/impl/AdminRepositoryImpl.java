package ir.example.finalPart03.repository.impl;

import ir.example.finalPart03.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class AdminRepositoryImpl {

    private EntityManager entityManager;

    public List<Specialist> findAllSpecialistsByCriteria(String firstname, String lastname, String email, String averageScoresOrderBy, String specialistField) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
// استفاده می‌کنیم زیرا ما در اینجا تنها به دنبال فیلتر کردن Specialist ها هستیم
        CriteriaQuery<Specialist> criteriaQuery = criteriaBuilder.createQuery(Specialist.class);
        Root<Specialist> specialistRoot = criteriaQuery.from(Specialist.class);

        List<Predicate> predicates = new ArrayList<>();
        ArrayList<Order> orderList = new ArrayList<>();

// فقط برای Specialist ها چک می‌کنیم، پس نیازی به بررسی userRole نیست.

        if (firstname != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("firstname"), "%" + firstname + "%"));
        }

        if (lastname != null) {
            predicates.add(criteriaBuilder.like(specialistRoot.get("lastname"), "%" + lastname + "%"));
        }

        if (email != null) {
            predicates.add(criteriaBuilder.equal(specialistRoot.get("email"), email));
        }

        if (specialistField != null) {
            // Join برای رسیدن به نام سرویس در entity Services
            Join<Specialist, SubServices> subServiceJoin = specialistRoot.join("subServices", JoinType.INNER);
            Join<SubServices, Services> serviceJoin = subServiceJoin.join("services", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(serviceJoin.get("serviceName"), specialistField));
        }

// تعیین ترتیب مرتب‌سازی بر اساس averageScores
        if (averageScoresOrderBy != null) {
            if (averageScoresOrderBy.equalsIgnoreCase("asc")) {
                orderList.add(criteriaBuilder.asc(specialistRoot.get("averageScores")));
            } else if (averageScoresOrderBy.equalsIgnoreCase("desc")) {
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


//                criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.type(), Specialist.class));
//            } else if (userRole.equals("Customer")) {
//                CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
//                Root<Customer> userRoot = criteriaQuery.from(Customer.class);
//
//                criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.type(), Customer.class));


//        if (firstname != null) {
//            predicates.add(criteriaBuilder.like(specialistRoot.get("firstname"), "%" + firstname + "%"));
//        }
//
//        if (lastname != null) {
//            predicates.add(criteriaBuilder.like(specialistRoot.get("lastname"), "%" + lastname + "%"));
//        }
//
//        if (email != null) {
////            predicates.add(criteriaBuilder.equal(userRoot.get("email"), email));
//            predicates.add(criteriaBuilder.like(specialistRoot.get("email"), "%" + email + "%"));
//
//        }


//        if (minScore != null && maxScore != null) {
//            predicates.add(criteriaBuilder.between(userRoot.get("averageScores"), minScore, maxScore));
//        }

//        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
//
//        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
//
//        return query.getResultList();
//    }


//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
//
//
//        Root<Users> root = criteriaQuery.from(Users.class);
//        Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");
//        Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");
//        Predicate emailPredicate = criteriaBuilder.like(root.get("email"), "%" + email + "%");
//        Predicate averageScorePredicate = criteriaBuilder.like(root.get("averageScores"), "%" + averageScores + "%");
//
//        Predicate orPredicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate, emailPredicate, averageScorePredicate);
//        Predicate andPredicate = criteriaBuilder.and(firstnamePredicate, lastnamePredicate, emailPredicate, averageScorePredicate);
//
//        criteriaQuery.where(orPredicate);
//
//        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
//        return query.getResultList();
//    }


}
