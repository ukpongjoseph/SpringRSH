package dev.joseph.playground.thymeleaf_demo.Repo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.joseph.playground.thymeleaf_demo.Model.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
