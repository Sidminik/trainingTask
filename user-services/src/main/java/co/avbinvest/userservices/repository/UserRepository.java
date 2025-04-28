package co.avbinvest.userservices.repository;

import co.avbinvest.userservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
