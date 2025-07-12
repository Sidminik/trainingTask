package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.request.CompanyRequestDTO;
import co.avbinvest.companyservices.dto.response.CompanyBasicDTO;
import co.avbinvest.companyservices.dto.response.CompanyWithUsersDTO;
import co.avbinvest.companyservices.dto.response.UserDTO;
import co.avbinvest.companyservices.exception.CompanyNotFoundException;
import co.avbinvest.companyservices.mapper.CompanyMapper;
import co.avbinvest.companyservices.model.Company;
import co.avbinvest.companyservices.repository.CompanyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final CompanyRepository companyRepository;
    private final UserServiceClient userServiceClient;
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              UserServiceClient userServiceClient,
                              CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.userServiceClient = userServiceClient;
        this.companyMapper = companyMapper;
    }

    @Override
    public List<CompanyWithUsersDTO> getAllCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companiesPage = companyRepository.findAll(pageable);

        List<CompanyWithUsersDTO> companiesDTOs = companiesPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("Converted companies to DTOs: {}", companiesDTOs.size());
        return companiesDTOs;
    }

    @Override
    public CompanyWithUsersDTO getCompanyById(Long id) {
        Company company = findCompanyOrThrow(id);
        logger.info("Company found: {}", company);
        return convertToDTO(company);
    }

    @Override
    public CompanyWithUsersDTO createCompany(CompanyRequestDTO companyDetails) {
        Company company = companyMapper.createModel(companyDetails);
        companyRepository.save(company);
        logger.info("A new Company has been created with ID: {}", company.getId());
        return convertToDTO(company);
    }

    @Override
    public CompanyWithUsersDTO updateCompany(Long id, CompanyRequestDTO companyDetails) {
        Company company = findCompanyOrThrow(id);
        companyMapper.updateModel(companyDetails, company);
        company = companyRepository.save(company);
        logger.info("A CompanyDTO instance has been updated for Company ID: {}", id);
        return convertToDTO(company);
    }

    @Override
    public void deleteCompany(Long id) {
        findCompanyOrThrow(id);
        companyRepository.deleteById(id);
        logger.info("Company with ID: {} has been deleted successfully", id);
    }

    private CompanyWithUsersDTO convertToDTO(Company company) {
        CompanyBasicDTO basicDto = companyMapper.responseBasicDto(company);
        List<UserDTO> users = userServiceClient.getUsersByCompanyId(company.getId());
        return companyMapper.responseDtoWithUsers(basicDto, users);
    }

    private Company findCompanyOrThrow(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            logger.error("Company not found with id: {}", id);
            throw new CompanyNotFoundException(id);
        }
        return company;
    }
}
