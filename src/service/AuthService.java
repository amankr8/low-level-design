package service;

import entity.User;

public interface AuthService {

    void signUp(String username, String password) throws Exception;

    User signIn(String username, String password) throws Exception;
}
