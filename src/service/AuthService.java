package service;

public interface AuthService {

    void signUp(String username, String password) throws Exception;

    boolean signIn(String username, String password);
}
