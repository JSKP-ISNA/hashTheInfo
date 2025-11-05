package com.example.secureapp.service;

import com.example.secureapp.dto.UserDto;
import com.example.secureapp.exception.ResourceAlreadyExistsException;
import com.example.secureapp.mapper.UserMapper;
import com.example.secureapp.model.User;
import com.example.secureapp.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public User register(UserDto dto) {
        if (repo.existsByUsername(dto.getUsername()))
            throw new ResourceAlreadyExistsException("Username already exists");
        if (repo.existsByEmail(dto.getEmail()))
            throw new ResourceAlreadyExistsException("Email already exists");

        User u = UserMapper.toEntity(dto);
        u.setPassword(encoder.encode(dto.getPassword()));
        if (u.getRole() == null || u.getRole().isBlank()) u.setRole("USER");
        u.setCreatedAt(LocalDateTime.now());
        return repo.save(u);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
