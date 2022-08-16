package ru.yandex.practicum.fillmorate.model.validation;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterDateValidator.class)
@Documented
public @interface AfterDate {
    String message() default "{AfterDate.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}