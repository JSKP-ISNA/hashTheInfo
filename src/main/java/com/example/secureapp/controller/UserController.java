package com.example.secureapp.controller;

import com.example.secureapp.dto.UserDto;
import com.example.secureapp.mapper.UserMapper;
import com.example.secureapp.model.User;
import com.example.secureapp.service.UserService;
import java.security.Principal;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Small helper API to fetch the current user’s profile */
@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/api/me")
    public ResponseEntity<UserDto> me(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();
        Optional<User> u = userService.findByUsername(principal.getName());
        return u.map(user -> ResponseEntity.ok(UserMapper.toDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
