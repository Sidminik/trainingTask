package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyServiceClient {

    private final RestTemplate restTemplate;
    private final String companyServiceBaseUrl;

    @Autowired
    public CompanyServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.companyServiceBaseUrl = "http://localhost:8082/api/companies";
    }

    public CompanyDTO getCompanyById(Long companyId) {
        try {
            String url = companyServiceBaseUrl + "/" + companyId;
            return restTemplate.getForObject(url, CompanyDTO.class);
        } catch (ResourceAccessException e) {
            System.err.println("Error accessing the company's service: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error when requesting a company: " + e.getMessage());
            return null;
        }
    }
}
