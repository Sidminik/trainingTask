package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.*;
import co.avbinvest.userservices.model.User;
import co.avbinvest.userservices.repository.UserRepository;
import co.avbinvest.userservices.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final CompanyServiceClient companyServiceClient;

    public UserService(CompanyServiceClient companyServiceClient) {
        this.companyServiceClient = companyServiceClient;
    }

    public List<UserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return convertToDTO(user);
    }

    public UserDTO createUser(Map<String, Object> userDetails) {
        User user = new User();
        user.setFirstName((String) userDetails.get("firstName"));
        user.setLastName((String) userDetails.get("lastName"));
        user.setPhoneNumber((String) userDetails.get("phoneNumber"));

        Object companyIdObj = userDetails.get("companyId");
        Long companyId = null;
        if (companyIdObj instanceof Number) {
            companyId = ((Number) companyIdObj).longValue();
        }
        user.setCompanyId(companyId);

        return convertToDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());

        if (userDetails.getCompanyId() != null) {
            user.setCompanyId(userDetails.getCompanyId());
        }

        return convertToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        if (user.getCompanyId() != null) {
            CompanyDTO companyDTO = companyServiceClient.getCompanyById(user.getCompanyId());
            if (companyDTO != null) {
                userDTO.setCompanyDTO(companyDTO);
            }
        }
        return userDTO;
    }
}