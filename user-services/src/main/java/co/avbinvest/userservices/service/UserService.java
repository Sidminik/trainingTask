package co.avbinvest.userservices.service;

import co.avbinvest.userservices.dto.response.UserBasicDTO;
import co.avbinvest.userservices.dto.response.UserWithCompanyDTO;
import co.avbinvest.userservices.dto.request.UserRequestDTO;

import java.util.List;

public interface UserService {
    List<UserWithCompanyDTO> getAllUsers(int page, int size);
    UserWithCompanyDTO getUserById(Long id);
    UserWithCompanyDTO createUser(UserRequestDTO userDetails);
    UserWithCompanyDTO updateUser(Long id, UserRequestDTO userDetails);
    void deleteUser(Long id);
    List<UserBasicDTO> getCompanyIdFilterUsers(Long companyID);
}
