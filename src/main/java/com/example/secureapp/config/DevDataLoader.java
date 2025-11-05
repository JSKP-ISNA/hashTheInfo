package com.example.secureapp.config;

import com.example.secureapp.model.User;
import com.example.secureapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DevDataLoader {

    @Bean
    CommandLineRunner seedDefaultUser(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            String u = "demo";
            String e = "demo@example.com";
            if (!repo.existsByUsername(u) && !repo.existsByEmail(e)) {
                User user = new User();
                user.setUsername(u);
                user.setEmail(e);
                user.setPhone("0000000000");
                user.setRole("USER");
                user.setPassword(encoder.encode("demo123"));
                user.setCreatedAt(LocalDateTime.now());
                repo.save(user);
                System.out.println("Seeded user -> username: demo  password: demo123");
            }
        };
    }
}
