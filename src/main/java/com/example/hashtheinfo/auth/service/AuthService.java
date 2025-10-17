package com.example.hashtheinfo.auth.service;

import com.example.hashtheinfo.user.model.User;
import com.example.hashtheinfo.user.service.UserService;
import com.example.hashtheinfo.security.util.EncryptionUtil;
import com.example.hashtheinfo.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> login(String username, String password) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) return Optional.empty();
        User user = userOpt.get();
        if (EncryptionUtil.verify(password, user.getPasswordHash())) {
            return Optional.of(jwtUtil.generateToken(user.getUsername()));
        }
        return Optional.empty();
    }
}
