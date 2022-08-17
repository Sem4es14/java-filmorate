package ru.yandex.practicum.fillmorate.model.validation;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {
    public final void initialize(final AfterDate annotation) {}

    public final boolean isValid(final LocalDate value, final ConstraintValidatorContext context) {
        LocalDate date = LocalDate.of(1895, 12, 28);
        return value.isAfter(date);
    }
}
