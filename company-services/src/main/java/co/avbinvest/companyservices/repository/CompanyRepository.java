package co.avbinvest.companyservices.repository;

import co.avbinvest.companyservices.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
