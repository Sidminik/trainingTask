package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.response.CompanyWithUsersDTO;
import co.avbinvest.companyservices.dto.request.CompanyRequestDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyWithUsersDTO> getAllCompanies(int page, int size);
    CompanyWithUsersDTO getCompanyById(Long id);
    CompanyWithUsersDTO createCompany(CompanyRequestDTO companyDetails);
    CompanyWithUsersDTO updateCompany(Long id, CompanyRequestDTO companyDetails);
    void deleteCompany(Long id);
}
