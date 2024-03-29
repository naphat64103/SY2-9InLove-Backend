package sit.project.projectv1.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import java.util.regex.*;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private Pattern regexpPattern;
    private String fieldName = "password";

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void initialize(Password constraintAnnotation) {
        regexpPattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            throw new ResponseStatusValidationException(HttpStatus.BAD_REQUEST, fieldName, "must not be blank");
        }

        if (!(value.length() >= 8 && value.length() <= 14)) {
            throw new ResponseStatusValidationException(HttpStatus.BAD_REQUEST, fieldName, "size must be between 8 and 14");
        }

        if (regexpPattern.matcher(value).matches() == false) {
            throw new ResponseStatusValidationException(HttpStatus.BAD_REQUEST, fieldName,
                    "must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters");
        }

        return true;
    }
}
