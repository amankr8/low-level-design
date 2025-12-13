package service.impl;

import entity.User;
import repository.UserRepository;
import service.AuthService;

import java.util.Base64;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(String username, String password) throws Exception {
        try {
            if (userRepository.getUserByUsername(username).isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            User newUser = new User(username, encodedPassword);
            userRepository.saveUser(newUser);
        } catch (Exception e) {
            throw new Exception("Error during sign up: " + e.getMessage());
        }
    }

    @Override
    public User signIn(String username, String password) throws Exception {
        try {
            var userOpt = userRepository.getUserByUsername(username);
            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found with username: " + username);
            }

            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            if (userOpt.get().getPassword().equals(encodedPassword)) {
                return userOpt.get();
            } else {
                throw new RuntimeException("Invalid credentials.");
            }
        } catch (Exception e) {
            throw new Exception("Error during sign in: " + e.getMessage());
        }
    }
}
