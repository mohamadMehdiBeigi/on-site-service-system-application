package ir.example.finalPart03.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminRepositoryImpl {




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
