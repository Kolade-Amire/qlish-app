package com.qlish.qlish_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class OptionValidator implements ConstraintValidator<ValidOption, String> {
    private static final Set<String> OPTIONS = Set.of("A", "B", "C", "D");
    @Override
    public boolean isValid(String option, ConstraintValidatorContext context) {
        return OPTIONS.contains(option);
    }
}
