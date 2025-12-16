package repository;

import entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository implements BaseRepository<Integer, User> {
    private final AtomicInteger nextId;
    private final Map<Integer, User> userData;
    private final Map<String, Integer> usernameToIdMap;

    public UserRepository() {
        nextId = new AtomicInteger(1);
        this.userData = new ConcurrentHashMap<>();
        this.usernameToIdMap = new ConcurrentHashMap<>();
    }

    public Optional<User> findByUsername(String username) {
        if (usernameToIdMap.containsKey(username)) {
            int userId = usernameToIdMap.get(username);
            return Optional.ofNullable(userData.get(userId));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(userData.get(id));
    }

    @Override
    public List<User> findAll() {
        return userData.values().stream().toList();
    }

    @Override
    public User save(User user) {
        if (!userData.containsKey(user.getUserId())) {
            int id = nextId.getAndIncrement();
            user.setUserId(id);
        }
        userData.put(user.getUserId(), user);
        usernameToIdMap.put(user.getUsername(), user.getUserId());
        return user;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
