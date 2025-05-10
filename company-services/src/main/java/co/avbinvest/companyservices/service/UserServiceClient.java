package co.avbinvest.companyservices.service;

import co.avbinvest.companyservices.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceBaseUrl;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.userServiceBaseUrl = "http://localhost:8081/api/users";
    }

    public List<UserDTO> getUsersByCompanyId(Long companyId) {
        String apiUrl = userServiceBaseUrl + "/company?id=" + companyId;
        ResponseEntity<UserDTO[]> response = restTemplate.getForEntity(apiUrl, UserDTO[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
