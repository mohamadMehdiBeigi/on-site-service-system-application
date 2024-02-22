package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {


    @Query(" from Comments c " +
            "join c.order.subServices s " +
            "join s.specialists ss " +
            "where ss.id =:specialistId")
    List<Comments> findAllBySpecialistId(Long specialistId);

}
