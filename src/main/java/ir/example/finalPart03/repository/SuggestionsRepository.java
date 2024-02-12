package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionsRepository extends JpaRepository<Suggestions, Long> {

    @Query(nativeQuery = true, value = "select s.duration_of_daily_work , s.suggested_price , s.suggested_time_to_start_work , s.suggestions_registration_date from final_part3.suggestions s join final_part3.\"order\" o on o.id = s.order_id ORDER BY s.suggested_price")
    List<Suggestions> findAllByCustomerIdOrderBySuggestedPrice(Long customerId);


    @Query(nativeQuery = true, value = "select s.* from final_part3.suggestions s join final_part3.\"order\" o on o.id = s.order_id join final_part3.users u on u.id = s.specialist_id where customer_id =:customerId ORDER BY u.average_scores")
    List<Suggestions> findAllByCustomerIdOrderByTotalScores(Long customerId);

}
