package t1.homeworks.synthetichumancore.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import t1.homeworks.synthetichumancore.constant.Priority;

public class EnumValidator implements ConstraintValidator<ValidEnum, Priority> {
    private static final Logger logger = LoggerFactory.getLogger(EnumValidator.class);

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Priority value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        try {
            Enum<?>[] constants = (Enum<?>[]) enumClass.getMethod("values").invoke(null);
            for (Enum<?> constant : constants) {
                if (constant.equals(value)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Error while validating enum value: {}", value, e);
            return false;
        }
    }
}
