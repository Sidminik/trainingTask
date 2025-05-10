package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.*;
import co.avbinvest.companyservices.exception.CompanyNotFoundException;
import co.avbinvest.companyservices.model.Company;
import co.avbinvest.companyservices.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private final UserServiceClient userServiceClient;

    public CompanyService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public List<CompanyDTO> getAllCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companiesPage = companyRepository.findAll(pageable);
        return companiesPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new CompanyNotFoundException(id);
        }
        return convertToDTO(company);
    }

    public CompanyDTO createCompany(ValidCompanyDTO companyDetails) {
        Company company = new Company();
        company.setName(companyDetails.getName());
        company.setBudget(companyDetails.getBudget());

        return convertToDTO(companyRepository.save(company));
    }

    public CompanyDTO updateCompany(Long id, ValidCompanyDTO companyDetails) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new CompanyNotFoundException(id);
        }

        company.setName(companyDetails.getName());
        company.setBudget(companyDetails.getBudget());

        return convertToDTO(companyRepository.save(company));
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            throw new CompanyNotFoundException(id);
        }
        companyRepository.deleteById(id);
    }

    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setBudget(company.getBudget());

        List<UserDTO> users = userServiceClient.getUsersByCompanyId(company.getId());
        companyDTO.setUsers(users);

        return companyDTO;
    }
}
