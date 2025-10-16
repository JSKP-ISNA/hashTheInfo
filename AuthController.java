package com.example.authmodule.controller;

import com.example.authmodule.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService = new AuthService();

    // -------- USER ENDPOINTS --------
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        return authService.registerUser(username, password);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        return authService.loginUser(username, password);
    }

    // -------- ADMIN ENDPOINTS --------
    @PostMapping("/admin/register")
    public String registerAdmin(@RequestParam String adminName, @RequestParam String password) {
        return authService.registerAdmin(adminName, password);
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam String adminName, @RequestParam String password) {
        return authService.loginAdmin(adminName, password);
    }

    // -------- LOGOUT --------
    @PostMapping("/logout")
    public String logout(@RequestParam String username) {
        return authService.logout(username);
    }
}