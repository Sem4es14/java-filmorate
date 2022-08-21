package ru.yandex.practicum.fillmorate.requests.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WithoutWhitespaceValidator implements ConstraintValidator<WithoutWhitespace, String> {
    public final void initialize(final WithoutWhitespace annotation) {}

    public final boolean isValid(final String value, final ConstraintValidatorContext context) {

        return !value.contains(" ");
    }
}
