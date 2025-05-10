package co.avbinvest.userservices.controller;

import co.avbinvest.userservices.dto.SimpleUserDTO;
import co.avbinvest.userservices.dto.UserDTO;
import co.avbinvest.userservices.dto.ValidUserDTO;
import co.avbinvest.userservices.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<UserDTO> users = userService.getAllUsers(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/company")
    public ResponseEntity<List<SimpleUserDTO>> getCompanyIdFilterUsers(
            @RequestParam(value = "id") Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<SimpleUserDTO> users = userService.getCompanyIdFilterUsers(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody ValidUserDTO userDetails) {
        UserDTO createdUser = userService.createUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @Valid @RequestBody ValidUserDTO userDetails) {
        UserDTO updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        String responseMessage = "The user with the ID " + id + " has been deleted";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
    }
}
