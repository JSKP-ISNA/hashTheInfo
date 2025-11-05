package com.example.secureapp.service;

import com.example.secureapp.dto.UserDto;
import com.example.secureapp.model.User;
import java.util.Optional;

public interface UserService {
    User register(UserDto dto);
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
