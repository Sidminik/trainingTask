package co.avbinvest.userservices.controller;

import co.avbinvest.userservices.dto.response.UserBasicDTO;
import co.avbinvest.userservices.dto.response.UserWithCompanyDTO;
import co.avbinvest.userservices.dto.request.UserRequestDTO;
import co.avbinvest.userservices.service.UserServiceImpl;

import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserWithCompanyDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("Request to get all users. Parameters: page={}, size={}", page, size);

        List<UserWithCompanyDTO> users = userService.getAllUsers(page, size);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithCompanyDTO> getUserById(@PathVariable @Min(1) Long id) {
        logger.info("Request received to get user by ID: {}", id);

        UserWithCompanyDTO user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/company")
    public ResponseEntity<List<UserBasicDTO>> getCompanyIdFilterUsers(
            @RequestParam(value = "id") @Min(1) Long id,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("Request received to get users for company ID: {} with pagination - page: {}, size: {}",
                id, page, size);

        List<UserBasicDTO> users = userService.getCompanyIdFilterUsers(id);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserWithCompanyDTO> createUser(@Valid @RequestBody UserRequestDTO userDetails) {
        logger.info("Request received to create user with details: {}", userDetails);

        UserWithCompanyDTO createdUser = userService.createUser(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserWithCompanyDTO> updateUser(@PathVariable @Min(1) Long id,
                                                         @Valid @RequestBody UserRequestDTO userDetails) {

        logger.info("Request received to update user with ID: {} and details: {}", id, userDetails);
        UserWithCompanyDTO updatedUser = userService.updateUser(id, userDetails);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Min(1) Long id) {
        logger.info("Request received to delete user with ID: {}", id);

        userService.deleteUser(id);
        String responseMessage = "The user with the ID " + id + " has been deleted";

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
    }
}
