package co.avbinvest.userservices.repository;

import co.avbinvest.userservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCompanyId(Long companyId);
}
