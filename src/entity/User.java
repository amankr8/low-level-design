package entity;

public class User extends Updatable {
    private int userId;
    private String username;
    private String password;
    private UserRole role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.CUSTOMER;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
