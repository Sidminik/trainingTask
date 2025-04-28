package co.avbinvest.userservices.repository;

import co.avbinvest.userservices.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
