package com.example.secureapp.web;

import com.example.secureapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API endpoints for availability checks */
@RestController("apiUserController")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/exists/username")
    public ResponseEntity<Boolean> existsUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }

    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> existsEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}
