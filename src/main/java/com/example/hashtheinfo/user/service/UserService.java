package com.example.hashtheinfo.user.service;

import com.example.hashtheinfo.user.model.User;
import com.example.hashtheinfo.user.repository.UserRepository;
import com.example.hashtheinfo.security.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String plaintextPassword, String role) {
        String hashed = EncryptionUtil.hash(plaintextPassword);
        User user = new User(username, hashed, role);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
