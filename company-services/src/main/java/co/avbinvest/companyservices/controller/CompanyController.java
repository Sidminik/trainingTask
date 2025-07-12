package co.avbinvest.companyservices.controller;

import co.avbinvest.companyservices.dto.request.CompanyRequestDTO;
import co.avbinvest.companyservices.dto.response.CompanyWithUsersDTO;
import co.avbinvest.companyservices.service.CompanyServiceImpl;

import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyServiceImpl companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyWithUsersDTO> getAllCompanies(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("Request to get all companies: page={}, size={}", page, size);
        List<CompanyWithUsersDTO> companies = companyService.getAllCompanies(page, size);
        return companies;
    }

    @GetMapping("/{id}")
    public CompanyWithUsersDTO getCompanyById(@PathVariable @Min(1) Long id) {

        logger.info("Request to get company by id: {}", id);
        CompanyWithUsersDTO company = companyService.getCompanyById(id);
        return company;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyWithUsersDTO createCompany(@Valid @RequestBody CompanyRequestDTO companyDetails) {

        logger.info("Request to create company: {}", companyDetails);
        CompanyWithUsersDTO createdCompany = companyService.createCompany(companyDetails);
        return createdCompany;
    }

    @PutMapping("/{id}")
    public CompanyWithUsersDTO updateCompany(@PathVariable @Min (1) Long id,
                                             @Valid @RequestBody CompanyRequestDTO companyDetails) {

        logger.info("Request to update company with id: {}", id);
        CompanyWithUsersDTO updatedUser = companyService.updateCompany(id, companyDetails);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable @Min(1) Long id) {

        logger.info("Request to delete company with id: {}", id);
        companyService.deleteCompany(id);
        String responseMessage = "The company with the ID " + id + " has been deleted";
        return responseMessage;
    }
}
