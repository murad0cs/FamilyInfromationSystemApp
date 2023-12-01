package com.app.userinformationsystem.service;

import com.app.userinformationsystem.model.User;
import com.app.userinformationsystem.model.UserType;
import com.app.userinformationsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    public List<User> getAllChildUsers() {
        return userRepository.findByUserType(UserType.CHILD);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User createChildUser(Long parentId, User childUser) {
        Optional<User> optionalParent = userRepository.findById(parentId);

        if (optionalParent.isPresent()) {
            if(optionalParent.get().getUserType()== UserType.valueOf("PARENT")){
                User parent = optionalParent.get();
                childUser.setUserType(UserType.CHILD);
                childUser.setParent(parent);
                childUser.setAddress(parent.getAddress());
                return userRepository.save(childUser);
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Parent id not found"
            );
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Parent id Not Found");
    }

    public User updateUser(Long userId, User updatedUser)  {

        logger.info("Updating user with ID: {}", userId);
            User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setUserType(updatedUser.getUserType());
            existingUser.setParent(updatedUser.getParent());

            userRepository.save(existingUser);
            return existingUser;
    }

    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        userRepository.deleteById(userId);
    }
}
