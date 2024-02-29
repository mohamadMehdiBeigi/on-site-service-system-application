package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionsRepository extends JpaRepository<Suggestions, Long> {

    @Query(" from Suggestions s " +
            "join s.order o " +
            "join o.customer c " +
            "where c.id =:customerId " +
            "order by s.suggestedPrice desc ")
    List<Suggestions> findAllByCustomerIdOrderBySuggestedPrice(Long customerId);


    @Query(" from Suggestions s " +
            "join s.order.customer c " +
            "where c.id =:customerId " +
            "order by s.specialist.averageScores desc ")
    List<Suggestions> findAllByCustomerIdOrderByTotalScores(Long customerId);

    List<Suggestions> findAllBySpecialistId(Long specialistId);
}
