package com.example.authmodule.repository;

import com.example.authmodule.model.User;
import java.util.HashMap;

public class UserRepository {
    private final HashMap<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}