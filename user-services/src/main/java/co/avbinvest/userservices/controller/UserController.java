package co.avbinvest.userservices.controller;

import co.avbinvest.userservices.dto.SimpleUserDTO;
import co.avbinvest.userservices.dto.UserDTO;
import co.avbinvest.userservices.dto.ValidUserDTO;
import co.avbinvest.userservices.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Request to get all users. Parameters: page={}, size={}", page, size);

        List<UserDTO> users = userService.getAllUsers(page, size);

        logger.info("Number of users received: {}", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Request received to get user by ID: {}", id);

        UserDTO user = userService.getUserById(id);

        logger.info("Successfully retrieved user: {}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/company")
    public ResponseEntity<List<SimpleUserDTO>> getCompanyIdFilterUsers(
            @RequestParam(value = "id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Request received to get users for company ID: {} with pagination - page: {}, size: {}",
                id, page, size);

        List<SimpleUserDTO> users = userService.getCompanyIdFilterUsers(id);

        logger.info("Number of users retrieved for company ID {}: {}", id, users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody ValidUserDTO userDetails) {
        logger.info("Request received to create user with details: {}", userDetails);

        UserDTO createdUser = userService.createUser(userDetails);

        logger.info("User created successfully with ID: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @Valid @RequestBody ValidUserDTO userDetails) {

        logger.info("Request received to update user with ID: {} and details: {}", id, userDetails);
        UserDTO updatedUser = userService.updateUser(id, userDetails);

        logger.info("User with ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("Request received to delete user with ID: {}", id);

        userService.deleteUser(id);
        String responseMessage = "The user with the ID " + id + " has been deleted";
        logger.info("Successful deletion of user with ID: {}", id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
    }
}
