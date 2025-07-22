package t1.homeworks.tokenauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t1.homeworks.tokenauth.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
