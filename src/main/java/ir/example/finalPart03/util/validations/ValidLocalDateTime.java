package ir.example.finalPart03.util.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateTimeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocalDateTime {

    String message() default "Invalid LocalDateTime, type a date after 1990/1/1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}