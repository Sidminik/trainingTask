package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.response.CompanyDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceClient.class);

    private final RestTemplate restTemplate;
    private final String companyServiceBaseUrl;

    @Autowired
    public CompanyServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.companyServiceBaseUrl = "http://host.docker.internal:8082/api/companies";
    }

    public CompanyDTO getCompanyById(Long companyId) {
        try {
            String url = companyServiceBaseUrl + "/" + companyId;
            CompanyDTO companyDTO = restTemplate.getForObject(url, CompanyDTO.class);

            if (companyDTO != null) {
                logger.info("Received response for company ID {}: {}", companyId, companyDTO);
            } else {
                logger.warn("No company found for ID: {}", companyId);
            }
            return companyDTO;
        } catch (ResourceAccessException e) {
            logger.error("Error accessing the company's service: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error when requesting a company: {}", e.getMessage());
            return null;
        }
    }
}
