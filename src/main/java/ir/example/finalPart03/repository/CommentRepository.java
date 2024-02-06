package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

    @Query(nativeQuery = true, value =
            "select c.* " +
                    "from final_part3.comments c " +
                    "join final_part3.\"order\" o on c.id = o.comments_id " +
                    "join final_part3.sub_services ss on ss.id = o.sub_services_id " +
                    "join final_part3.users_sub_services uss on ss.id = uss.sub_service_id " +
                    "join final_part3.users u on u.id = uss.user_id " +
                    "where dtype = 'Specialist' " +
                    "and u.id =:specialistId")
    List<Comments> findAllBySpecialistId(Long specialistId);

}
