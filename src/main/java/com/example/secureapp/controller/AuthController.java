package com.example.secureapp.controller;

import com.example.secureapp.dto.UserDto;
import com.example.secureapp.exception.ResourceAlreadyExistsException;
import com.example.secureapp.service.UserService;
import com.example.secureapp.util.ValidationUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {

        if (error != null)  model.addAttribute("error", "Invalid username or password");
        if (logout != null) model.addAttribute("message", "Logged out successfully");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("user") UserDto dto,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) return "register";
        
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
            return "register";
        }
        ValidationUtils.validateDob(dto.getDob(), result);
        if (result.hasErrors()) return "register";

        try {
            userService.register(dto);
        } catch (ResourceAlreadyExistsException ex) {
            result.rejectValue("username", "exists", ex.getMessage());
            return "register";
        }

        model.addAttribute("message", "Registration successful. Please log in.");
        return "login";
    }
}
