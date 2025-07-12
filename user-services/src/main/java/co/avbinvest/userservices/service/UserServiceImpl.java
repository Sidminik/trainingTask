package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.response.UserBasicDTO;
import co.avbinvest.userservices.dto.response.UserWithCompanyDTO;
import co.avbinvest.userservices.dto.request.UserRequestDTO;
import co.avbinvest.userservices.dto.response.CompanyDTO;
import co.avbinvest.userservices.mapper.UserMapper;
import co.avbinvest.userservices.model.User;
import co.avbinvest.userservices.repository.UserRepository;
import co.avbinvest.userservices.exception.UserNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final CompanyServiceClient companyServiceClient;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           CompanyServiceClient companyServiceClient,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.companyServiceClient = companyServiceClient;
        this.userMapper = userMapper;
    }

    public List<UserWithCompanyDTO> getAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserWithCompanyDTO> userDTOs = usersPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("Converted to DTO users: {}", userDTOs.size());

        return userDTOs;
    }

    public UserWithCompanyDTO getUserById(Long id) {
        User user = findUserOrThrow(id);
        logger.info("User found: {}", user);
        return convertToDTO(user);
    }

    public UserWithCompanyDTO createUser(UserRequestDTO userDetails) {
        User user = userMapper.createModel(userDetails);
        userRepository.save(user);
        logger.info("A new User has been created with ID: {}", user.getId());
        return convertToDTO(user);
    }

    public UserWithCompanyDTO updateUser(Long id, UserRequestDTO userDetails) {
        User user = findUserOrThrow(id);
        userMapper.updateModel(userDetails, user);
        user = userRepository.save(user);
        logger.info("A UserDTO instance has been updated for User ID: {}", id);
        return convertToDTO(user);
    }

    public void deleteUser(Long id) {
        findUserOrThrow(id);
        userRepository.deleteById(id);
        logger.info("User with ID: {} has been deleted successfully", id);
    }

    private UserWithCompanyDTO convertToDTO(User user) {
        UserBasicDTO userBasicDTO = userMapper.responseBasicDto(user);
        CompanyDTO companyDTO = companyServiceClient.getCompanyById(user.getCompanyId());
        return userMapper.responseDtoWithCompany(userBasicDTO, companyDTO);
    }

    public List<UserBasicDTO> getCompanyIdFilterUsers(Long companyID) {
        List<User> users = userRepository.findByCompanyId(companyID);
        List<UserBasicDTO> usersList = new ArrayList<>();

        for (User user : users) {
            usersList.add(userMapper.responseBasicDto(user));
        }
        logger.info("Converted {} users to UserBasicDTO", usersList.size());
        return usersList;
    }

    private User findUserOrThrow(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            logger.error("User not found with id: {}", id);
            throw new UserNotFoundException(id);
        }
        return user;
    }
}