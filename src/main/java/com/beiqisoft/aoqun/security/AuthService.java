package com.beiqisoft.aoqun.security;


import com.beiqisoft.aoqun.entity.User;

public interface AuthService {
    User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
    User getUserByname(String userName);
}
