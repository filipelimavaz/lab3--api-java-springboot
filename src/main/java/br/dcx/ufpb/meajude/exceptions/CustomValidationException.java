package br.dcx.ufpb.meajude.exceptions;


import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class CustomValidationException extends AbstractException {
    private Set<ConstraintViolation<Object>> violations;

    public CustomValidationException(String title, Set<ConstraintViolation<Object>> violations) {
        super(title, "One or more fields have validation errors");
        this.violations = violations;
    }

    public Set<ConstraintViolation<Object>> getViolations() {
        return violations;
    }
}



