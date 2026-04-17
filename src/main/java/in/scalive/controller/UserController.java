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

    // Constructor injection — preferred over @Autowired field injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ──────────────────────────────────────────────────────
    // GET /users  →  List all users
    // ──────────────────────────────────────────────────────

    /**
     * Returns a list of all users.
     * HTTP 200 OK with the list (may be empty — never 404 here).
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ──────────────────────────────────────────────────────
    // GET /users/{id}  →  Get a single user
    // ──────────────────────────────────────────────────────

    /**
     * Returns the user with the given ID.
     * HTTP 200 OK, or 404 Not Found if no such user exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // ──────────────────────────────────────────────────────
    // POST /users  →  Create a new user
    // ──────────────────────────────────────────────────────

    /**
     * Creates a new user from the JSON request body.
     *
     * @Valid triggers Bean Validation on the User object.
     * If any constraint fails, Spring throws MethodArgumentNotValidException
     * which GlobalExceptionHandler catches and returns HTTP 400.
     *
     * HTTP 201 Created on success, 400 on validation failure,
     * 409 Conflict if email already exists.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // ──────────────────────────────────────────────────────
    // PUT /users/{id}  →  Update an existing user
    // ──────────────────────────────────────────────────────

    /**
     * Replaces all fields of an existing user with the provided values.
     * HTTP 200 OK on success, 404 if not found, 409 if email conflict.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User updatedUser) {

        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // ──────────────────────────────────────────────────────
    // DELETE /users/{id}  →  Delete a user
    // ──────────────────────────────────────────────────────

    /**
     * Deletes the user with the given ID.
     * HTTP 204 No Content on success (no body returned).
     * HTTP 404 if the user doesn't exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
