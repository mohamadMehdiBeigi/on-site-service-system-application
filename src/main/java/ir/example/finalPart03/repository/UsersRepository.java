package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {
}
