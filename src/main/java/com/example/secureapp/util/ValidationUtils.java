package com.example.secureapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/** Extra form validations that aren’t covered by annotations */
public class ValidationUtils {

    public static void validateDob(String dobStr, BindingResult bindingResult) {
        if (dobStr == null || dobStr.isBlank()) return;
        try {
            LocalDate dob = LocalDate.parse(dobStr);
            if (dob.isAfter(LocalDate.now())) {
                bindingResult.addError(new FieldError("user", "dob", "DOB cannot be a future date"));
            }
        } catch (DateTimeParseException ex) {
            bindingResult.addError(new FieldError("user", "dob", "Invalid date format (use yyyy-MM-dd)"));
        }
    }
}
