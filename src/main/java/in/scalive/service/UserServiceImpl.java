package in.scalive.service;

import in.scalive.exception.DuplicateEmailException;
import in.scalive.exception.ResourceNotFoundException;
import in.scalive.model.User;
import in.scalive.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ──────────────────────────────────────────────
 * SERVICE LAYER (Implementation) — in.scalive.service
 * ──────────────────────────────────────────────
 * Contains all business logic for User management.
 *
 * @Service → marks this as a Spring-managed service bean.
 *            Spring will auto-detect it via component scan
 *            and inject it wherever UserService is needed.
 *
 * Constructor Injection is used here (preferred over @Autowired
 * on fields) because it makes dependencies explicit and
 * simplifies unit testing.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Constructor injection — Spring automatically provides the repository bean
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ──────────────────────────────────────────
    // GET ALL USERS
    // ──────────────────────────────────────────
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
        // Returns an empty list (not null) when no users exist — safe for the client
    }

    // ──────────────────────────────────────────
    // GET USER BY ID
    // ──────────────────────────────────────────
    @Override
    public User getUserById(Long id) {
        // orElseThrow: if no user found, throw our custom exception
        // GlobalExceptionHandler will catch it and return HTTP 404
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
    }

    // ──────────────────────────────────────────
    // CREATE USER
    // ──────────────────────────────────────────
    @Override
    public User createUser(User user) {
        // Business rule: email must be unique across all users
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(
                    "A user with email '" + user.getEmail() + "' already exists.");
        }
        return userRepository.save(user);
        // save() performs an INSERT because id is null at this point
    }

    // ──────────────────────────────────────────
    // UPDATE USER
    // ──────────────────────────────────────────
    @Override
    public User updateUser(Long id, User updatedUser) {
        // 1. Verify the user exists (throws 404 if not)
        User existingUser = getUserById(id);

        // 2. If the email is being changed, check it isn't taken by someone else
        boolean emailChanged = !existingUser.getEmail()
                                            .equalsIgnoreCase(updatedUser.getEmail());

        if (emailChanged && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new DuplicateEmailException(
                    "Email '" + updatedUser.getEmail() + "' is already in use by another user.");
        }

        // 3. Apply changes to the existing managed entity
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());

        // 4. save() performs an UPDATE because existingUser already has an id
        return userRepository.save(existingUser);
    }

    // ──────────────────────────────────────────
    // DELETE USER
    // ──────────────────────────────────────────
    @Override
    public void deleteUser(Long id) {
        // Verify user exists before attempting deletion (throws 404 if not)
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
