package repository;

import entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserByUsername(String username);

    void saveUser(User user);
}
