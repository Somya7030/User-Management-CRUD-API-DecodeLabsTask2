package in.scalive.service;

import in.scalive.model.User;

import java.util.List;


public interface UserService {


    List<User> getAllUsers();


    User getUserById(Long id);

    User createUser(User user);


    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);
}
