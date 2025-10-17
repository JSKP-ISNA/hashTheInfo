package com.example.authmodule.service;

import com.example.authmodule.model.User;
import com.example.authmodule.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();
    private final UserRepository adminRepository = new UserRepository(); // separate for admins
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ---------- USER AUTH ----------
    public String registerUser(String username, String plainPassword) {
        if (userRepository.findByUsername(username) != null) {
            return "❌ User already exists!";
        }
        String hashed = passwordEncoder.encode(plainPassword);
        userRepository.save(new User(username, hashed));
        return "✅ User registration successful for: " + username;
    }

    public String loginUser(String username, String plainPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) return "❌ User not found!";
        return passwordEncoder.matches(plainPassword, user.getHashedPassword()) ?
                "✅ User login successful for: " + username :
                "❌ Invalid user credentials!";
    }

    // ---------- ADMIN AUTH ----------
    public String registerAdmin(String adminName, String plainPassword) {
        if (adminRepository.findByUsername(adminName) != null) {
            return "❌ Admin already exists!";
        }
        String hashed = passwordEncoder.encode(plainPassword);
        adminRepository.save(new User(adminName, hashed));
        return "✅ Admin registration successful for: " + adminName;
    }

    public String loginAdmin(String adminName, String plainPassword) {
        User admin = adminRepository.findByUsername(adminName);
        if (admin == null) return "❌ Admin not found!";
        return passwordEncoder.matches(plainPassword, admin.getHashedPassword()) ?
                "✅ Admin login successful for: " + adminName :
                "❌ Invalid admin credentials!";
    }

    // ---------- LOGOUT ----------
    public String logout(String username) {
        return "👋 User/Admin " + username + " logged out successfully!";
    }
}