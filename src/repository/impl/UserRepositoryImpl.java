package repository.impl;

import entity.User;
import repository.UserRepository;
import util.RepositoryHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    Map<Integer, User> userData;
    Map<String, Integer> usernameToIdMap;

    public UserRepositoryImpl() {
        this.userData = new HashMap<>();
        this.usernameToIdMap = new HashMap<>();
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
            user.setUserId(RepositoryHelper.getNextIdForMap(userData));
        }
        userData.put(user.getUserId(), user);
        usernameToIdMap.put(user.getUsername(), user.getUserId());
    }
}
