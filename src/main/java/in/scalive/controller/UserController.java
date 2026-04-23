package in.scalive.controller;

import in.scalive.model.User;
import in.scalive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ──────────────────────────────────────────────
 * CONTROLLER LAYER — in.scalive.controller
 * ──────────────────────────────────────────────
 * Handles incoming HTTP requests and delegates
 * all business logic to the UserService.
 *
 * Controllers should be THIN — no business logic here.
 * They only:
 *   1. Accept the HTTP request & extract inputs
 *   2. Call the appropriate service method
 *   3. Return the HTTP response with correct status code
 *
 * @RestController = @Controller + @ResponseBody
 *   → every method return value is serialised to JSON automatically
 *
 * @RequestMapping("/users") → base URL prefix for all endpoints
 *
 * @CrossOrigin → allows calls from any frontend origin (e.g., React dev server).
 *   Restrict this in production: @CrossOrigin(origins = "https://yourfrontend.com")
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User updatedUser) {

        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
