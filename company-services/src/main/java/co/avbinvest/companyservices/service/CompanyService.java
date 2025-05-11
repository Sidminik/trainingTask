package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.*;
import co.avbinvest.companyservices.exception.CompanyNotFoundException;
import co.avbinvest.companyservices.model.Company;
import co.avbinvest.companyservices.repository.CompanyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private final UserServiceClient userServiceClient;

    public CompanyService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public List<CompanyDTO> getAllCompanies(int page, int size) {

        logger.info("Fetching all companies with pagination: page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companiesPage = companyRepository.findAll(pageable);
        logger.info("Received users from the repository: {}", companiesPage.getTotalElements());

        List<CompanyDTO> companiesDTOs = companiesPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("Converted companies to DTOs: {}", companiesDTOs.size());
        return companiesDTOs;
    }

    public CompanyDTO getCompanyById(Long id) {

        logger.info("Fetching company by id: {}", id);
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            logger.error("Company not found with id: {}", id);
            throw new CompanyNotFoundException(id);
        }

        logger.info("Company found: {}", company);
        return convertToDTO(company);
    }

    public CompanyDTO createCompany(ValidCompanyDTO companyDetails) {

        logger.info("Creating new company: {}", companyDetails);
        Company company = new Company();
        company.setName(companyDetails.getName());
        company.setBudget(companyDetails.getBudget());

        companyRepository.save(company);
        logger.info("A new Company has been created with ID: {}", company.getId());

        return convertToDTO(company);
    }

    public CompanyDTO updateCompany(Long id, ValidCompanyDTO companyDetails) {

        logger.info("Updating company with id: {}", id);
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            logger.error("Company not found with id: {}", id);
            throw new CompanyNotFoundException(id);
        }

        company.setName(companyDetails.getName());
        company.setBudget(companyDetails.getBudget());

        logger.info("A CompanyDTO instance has been updated for Company ID: {}", id);
        return convertToDTO(companyRepository.save(company));
    }

    public void deleteCompany(Long id) {

        logger.info("Deleting company with id: {}", id);
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            logger.error("Company not found with id: {}", id);
            throw new CompanyNotFoundException(id);
        }
        companyRepository.deleteById(id);
        logger.info("Company with ID: {} has been deleted successfully", id);
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
