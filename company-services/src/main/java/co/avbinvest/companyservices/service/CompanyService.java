package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.CompanyDTO;
import co.avbinvest.companyservices.model.Company;
import co.avbinvest.companyservices.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    public List<CompanyDTO> getAllCompanies() {
        return (List<CompanyDTO>) companyRepository.findAll().stream()
                .map(company -> new CompanyDTO(company.getId(), company.getName(), company.getBudget()))
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Company not found"));
        return new CompanyDTO(company.getId(), company.getName(), company.getBudget());
    }

    /*
    public List<Long> getEmployeeIdsByCompanyId(Long companyId) {
        return userRepository.findByCompanyID_Id(companyId)
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
    */

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company = companyRepository.save(company);
        return new CompanyDTO(company.getId(), company.getName(), company.getBudget());
    }

    public Company updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            // Обновление данных компании с использованием DTO
            company.setName(companyDTO.getName());
            company.setBudget(companyDTO.getBudget());
            return companyRepository.save(company);
        }
        return null; // Возвращаем null, если компания не найдена
    }

    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true; // Возвращаем true, если удаление прошло успешно
        }
        return false; // Возвращаем false, если компания не найдена
    }
}
