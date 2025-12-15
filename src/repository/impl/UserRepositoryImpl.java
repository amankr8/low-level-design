package repository.impl;

import entity.User;
import repository.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImpl implements UserRepository {
    private int nextId;
    Map<Integer, User> userData;
    Map<String, Integer> usernameToIdMap;

    public UserRepositoryImpl() {
        nextId = 1;
        this.userData = new ConcurrentHashMap<>();
        this.usernameToIdMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        if (usernameToIdMap.containsKey(username)) {
            int userId = usernameToIdMap.get(username);
            return Optional.ofNullable(userData.get(userId));
        }
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        if (user.getUserId() == 0) {
            user.setUserId(nextId++);
        }
        userData.put(user.getUserId(), user);
        usernameToIdMap.put(user.getUsername(), user.getUserId());
    }
}
