package co.avbinvest.companyservices.repository;

import co.avbinvest.companyservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCompanyID_Id(Long companyId);
}
