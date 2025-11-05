package com.example.secureapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException ex, Model model) {
        StringBuilder sb = new StringBuilder("Validation errors:");
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            sb.append(" [").append(fe.getField()).append("] ").append(fe.getDefaultMessage());
        }
        model.addAttribute("error", sb.toString());
        return "register";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public String handleExists(ResourceAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "register";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("error", "Unexpected error: " + ex.getMessage());
        return "error";
    }
}
