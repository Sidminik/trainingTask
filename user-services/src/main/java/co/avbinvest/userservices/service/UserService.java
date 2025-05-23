package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.*;
import co.avbinvest.userservices.model.User;
import co.avbinvest.userservices.repository.UserRepository;
import co.avbinvest.userservices.exception.UserNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final CompanyServiceClient companyServiceClient;

    public UserService(CompanyServiceClient companyServiceClient) {
        this.companyServiceClient = companyServiceClient;
    }

    public List<UserDTO> getAllUsers(int page, int size) {
        logger.info("Running the getAllUsers method with parameters: page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        logger.info("Received users from the repository: {}", usersPage.getTotalElements());

        List<UserDTO> userDTOs = usersPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("Converted to DTO users: {}", userDTOs.size());

        return userDTOs;
    }

    public UserDTO getUserById(Long id) {
        logger.info("Run method getUserById for User id #{}", id);

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            logger.error("User not found with ID: {}", id);
            throw new UserNotFoundException(id);
        }

        logger.info("User found: {}", user);
        return convertToDTO(user);
    }

    public UserDTO createUser(ValidUserDTO userDetails) {
        logger.info("Run method createUser for User {}", userDetails);

        User user = new User();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setCompanyId(userDetails.getCompanyId());

        userRepository.save(user);
        logger.info("A new User has been created with ID: {}", user.getId());

        return convertToDTO(user);
    }

    public UserDTO updateUser(Long id, ValidUserDTO userDetails) {
        logger.info("Run method updateUser for User id #{}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            logger.error("User not found with ID: {}", id);
            throw new UserNotFoundException(id);
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setCompanyId(userDetails.getCompanyId());

        logger.info("A UserDTO instance has been updated for User ID: {}", id);
        return convertToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        logger.info("Run method deleteUser for User id #{}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            logger.error("User not found with ID: {}", id);
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        logger.info("User with ID: {} has been deleted successfully", id);
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

    public List<SimpleUserDTO> getCompanyIdFilterUsers(Long companyID) {
        logger.info("Run method getCompanyIdFilterUsers for company ID: {}", companyID);

        List<User> users = userRepository.findByCompanyId(companyID);
        logger.info("Number of users found for company ID {}: {}", companyID, users.size());

        List<SimpleUserDTO> simpleUsersDTO = new ArrayList<>();

        for (User companyUser : users) {
            SimpleUserDTO dto = new SimpleUserDTO();
            dto.setId(companyUser.getId());
            dto.setFirstName(companyUser.getFirstName());
            dto.setLastName(companyUser.getLastName());
            dto.setPhoneNumber(companyUser.getPhoneNumber());

            simpleUsersDTO.add(dto);
        }

        logger.info("Converted {} users to SimpleUserDTO", simpleUsersDTO.size());

        return simpleUsersDTO;
    }
}