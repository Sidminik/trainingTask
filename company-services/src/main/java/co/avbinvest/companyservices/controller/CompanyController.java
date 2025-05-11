package co.avbinvest.companyservices.controller;

import co.avbinvest.companyservices.dto.CompanyDTO;
import co.avbinvest.companyservices.dto.ValidCompanyDTO;
import co.avbinvest.companyservices.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Request to get all companies: page={}, size={}", page, size);

        List<CompanyDTO> companies = companyService.getAllCompanies(page, size);

        logger.info("Number of companies received: {}", companies.size());
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {

        logger.info("Request to get company by id: {}", id);
        CompanyDTO company = companyService.getCompanyById(id);

        logger.info("Successfully retrieved company: {}", company);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody ValidCompanyDTO companyDetails) {

        logger.info("Request to create company: {}", companyDetails);

        CompanyDTO createdCompany = companyService.createCompany(companyDetails);

        logger.info("Company created successfully with ID: {}", createdCompany.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id,
                                                    @Valid @RequestBody ValidCompanyDTO companyDetails) {

        logger.info("Request to update company with id: {}", id);

        CompanyDTO updatedUser = companyService.updateCompany(id, companyDetails);

        logger.info("Company with ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {

        logger.info("Request to delete company with id: {}", id);

        companyService.deleteCompany(id);
        String responseMessage = "The company with the ID " + id + " has been deleted";
        logger.info("Successful deletion of company with ID: {}", id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
    }
}
