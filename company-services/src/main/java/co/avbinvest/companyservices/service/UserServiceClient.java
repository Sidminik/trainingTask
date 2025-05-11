package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);

    private final RestTemplate restTemplate;
    private final String userServiceBaseUrl;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.userServiceBaseUrl = "http://localhost:8081/api/users";
    }

    public List<UserDTO> getUsersByCompanyId(Long companyId) {
        String apiUrl = userServiceBaseUrl + "/company?id=" + companyId;
        logger.info("Sending request to User Service: {}", apiUrl);

        ResponseEntity<UserDTO[]> response = restTemplate.getForEntity(apiUrl, UserDTO[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Received response from User Service: {}", (Object) response.getBody());
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            logger.error("Failed to fetch users for companyId {}. Status code: {}",
                    companyId, response.getStatusCode());
            return List.of();
        }
    }
}
