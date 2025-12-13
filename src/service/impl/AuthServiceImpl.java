package service.impl;

import entity.User;
import repository.UserRepository;
import service.AuthService;

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
            User newUser = new User(username, password);
            userRepository.saveUser(newUser);
        } catch (Exception e) {
            throw new Exception("Error during sign up: " + e.getMessage());
        }
    }

    @Override
    public boolean signIn(String username, String password) {
        return false;
    }
}
