package br.dcx.ufpb.meajude.exceptions;

import br.dcx.ufpb.meajude.entities.Campaign;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class CustomValidationException extends AbstractException {
    private Set<ConstraintViolation<Campaign>> violations;

    public CustomValidationException(String title, Set<ConstraintViolation<Campaign>> violations) {
        super(title, "One or more fields have validation errors");
        this.violations = violations;
    }

    public Set<ConstraintViolation<Campaign>> getViolations() {
        return violations;
    }
}



