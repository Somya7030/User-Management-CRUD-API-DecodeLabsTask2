package in.scalive.service;

import in.scalive.exception.DuplicateEmailException;
import in.scalive.exception.ResourceNotFoundException;
import in.scalive.model.User;
import in.scalive.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Constructor injection — Spring automatically provides the repository bean
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
        
    }

 
    @Override
    public User getUserById(Long id) {
       
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
    }

    
    @Override
    public User createUser(User user) {
      
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(
                    "A user with email '" + user.getEmail() + "' already exists.");
        }
        return userRepository.save(user);
        
    }

   
    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);       
        boolean emailChanged = !existingUser.getEmail()
                                            .equalsIgnoreCase(updatedUser.getEmail());

        if (emailChanged && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new DuplicateEmailException(
                    "Email '" + updatedUser.getEmail() + "' is already in use by another user.");
        }

       
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());

       
        return userRepository.save(existingUser);
    }

    
    @Override
    public void deleteUser(Long id) {
        
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
