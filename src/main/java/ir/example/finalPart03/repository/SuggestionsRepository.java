package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionsRepository extends JpaRepository<Suggestions, Long> {

    @Query(
            "select s " +
                    "from Suggestions s " +
                    "join s.order o " +
                    "where o.customer.id = :customerId " +
                    "order by s.SuggestedPrice"
    )
    List<Suggestions> findAllByCustomerIdOrderBySuggestedPrice(Long customerId);


    @Query(
            "select s " +
                    "from Suggestions s " +
                    "join s.order o " +
                    "where o.customer.id =:customerId " +
                    "order by s.specialist.averageScores "
    )
    List<Suggestions> findAllByCustomerIdOrderByTotalScores(Long customerId);
}
