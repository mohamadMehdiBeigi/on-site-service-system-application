package ir.example.finalPart03.util.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class LocalDateTimeValidator implements ConstraintValidator<ValidLocalDateTime, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        LocalDateTime minDateTime = LocalDateTime.of(1990, 1, 1, 0, 0);
        return value.isAfter(minDateTime) || value.isEqual(minDateTime);
    }
}
