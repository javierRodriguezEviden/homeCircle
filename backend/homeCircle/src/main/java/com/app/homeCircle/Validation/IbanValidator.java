package com.app.homeCircle.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Permitir valores vacíos
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Validar el formato del IBAN español
        return value.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$");
    }
}