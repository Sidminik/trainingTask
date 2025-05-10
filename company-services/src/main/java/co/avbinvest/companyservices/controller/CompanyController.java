package co.avbinvest.companyservices.controller;

import co.avbinvest.companyservices.dto.CompanyDTO;
import co.avbinvest.companyservices.dto.ValidCompanyDTO;
import co.avbinvest.companyservices.model.Company;
import co.avbinvest.companyservices.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<CompanyDTO> companies = companyService.getAllCompanies(page, size);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody ValidCompanyDTO companyDetails) {
        CompanyDTO createdUser = companyService.createCompany(companyDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id,
                                                    @Valid @RequestBody ValidCompanyDTO companyDetails) {
        CompanyDTO updatedUser = companyService.updateCompany(id, companyDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        String responseMessage = "The company with the ID " + id + " has been deleted";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
    }
}
