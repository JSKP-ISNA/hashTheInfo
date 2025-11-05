package com.example.secureapp.mapper;

import com.example.secureapp.dto.UserDto;
import com.example.secureapp.model.User;

import java.time.LocalDate;

/** Pure mapping; timestamps + encoding belong in the service */
public class UserMapper {

    public static User toEntity(UserDto dto) {
        if (dto == null) return null;
        User u = new User();
        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setEmail(dto.getEmail());
        u.setPhone(dto.getPhone());
        u.setRole(dto.getRole() == null || dto.getRole().isBlank() ? "USER" : dto.getRole());
        if (dto.getDob() != null && !dto.getDob().isBlank()) {
            u.setDob(LocalDate.parse(dto.getDob()));
        }
        return u;
    }

    public static UserDto toDto(User u) {
        if (u == null) return null;
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setPhone(u.getPhone());
        dto.setRole(u.getRole());
        if (u.getDob() != null) dto.setDob(u.getDob().toString());
        return dto;
    }
}
