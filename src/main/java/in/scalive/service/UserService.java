package in.scalive.service;

import in.scalive.model.User;

import java.util.List;

/**
 * ──────────────────────────────────────────────
 * SERVICE LAYER (Interface) — in.scalive.service
 * ──────────────────────────────────────────────
 * Defines the business-logic contract for User operations.
 *
 * Why use an interface?
 *   - Decouples the controller from the implementation
 *   - Makes the code easier to unit-test (you can mock the interface)
 *   - Follows the Dependency Inversion Principle (SOLID)
 *
 * The actual implementation is in UserServiceImpl.java
 */
public interface UserService {

    /** Returns all users from the database */
    List<User> getAllUsers();

    /** Returns a single user by ID; throws ResourceNotFoundException if missing */
    User getUserById(Long id);

    /** Creates and persists a new user; throws DuplicateEmailException if email already exists */
    User createUser(User user);

    /** Updates an existing user; throws ResourceNotFoundException if missing */
    User updateUser(Long id, User updatedUser);

    /** Deletes a user by ID; throws ResourceNotFoundException if missing */
    void deleteUser(Long id);
}
