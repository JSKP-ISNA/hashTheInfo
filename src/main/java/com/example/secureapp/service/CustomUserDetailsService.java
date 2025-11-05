package com.example.secureapp.service;

import com.example.secureapp.model.User;
import com.example.secureapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomUserDetailsService
 * -----------------------
 * Loads a user from the database and returns a Spring Security UserDetails.
 * IMPORTANT:
 *  - Do NOT encode the password here. Return the hash from DB as-is.
 *  - Build authorities with ROLE_ prefix (ex: ROLE_USER).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String role = (u.getRole() == null || u.getRole().isBlank()) ? "USER" : u.getRole().trim().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                List.of(authority)
        );
    }
}
