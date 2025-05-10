package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.*;
import co.avbinvest.userservices.model.User;
import co.avbinvest.userservices.repository.UserRepository;
import co.avbinvest.userservices.exception.UserNotFoundException;

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

    public UserDTO createUser(ValidUserDTO userDetails) {
        User user = new User();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setCompanyId(userDetails.getCompanyId());

        userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO updateUser(Long id, ValidUserDTO userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setCompanyId(userDetails.getCompanyId());

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

    public List<SimpleUserDTO> getCompanyIdFilterUsers(Long companyID) {
        List<User> users = userRepository.findByCompanyId(companyID);
        List<SimpleUserDTO> simpleUsersDTO = new ArrayList<>();

        for (User companyUser : users) {
            SimpleUserDTO dto = new SimpleUserDTO();
            dto.setId(companyUser.getId());
            dto.setFirstName(companyUser.getFirstName());
            dto.setLastName(companyUser.getLastName());
            dto.setPhoneNumber(companyUser.getPhoneNumber());

            simpleUsersDTO.add(dto);
        }
        return simpleUsersDTO;
    }
}