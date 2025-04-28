package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.model.*;
import co.avbinvest.companyservices.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Long> getEmployeeIdsByCompanyId(Long companyId) {
        return userRepository.findByCompanyID_Id(companyId)
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            company.setName(companyDetails.getName());
            company.setBudget(companyDetails.getBudget());
            return companyRepository.save(company);
        }
        return null;
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
