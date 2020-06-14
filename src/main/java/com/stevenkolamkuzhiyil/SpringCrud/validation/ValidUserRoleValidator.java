package com.stevenkolamkuzhiyil.SpringCrud.validation;

import com.stevenkolamkuzhiyil.SpringCrud.model.UserRoles;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class ValidUserRoleValidator implements ConstraintValidator<ValidUserRole, Object> {

    private String message;

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String roleName = (String) o;
            UserRoles.roleFromName(roleName);
            return true;
        } catch (IllegalArgumentException ex) {
            Optional<Field> field = Arrays.stream(o.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(ValidUserRole.class))
                    .filter(f -> {
                        try {
                            f.setAccessible(true);
                            return o.equals(f.get(o));
                        } catch (IllegalAccessException e) {
                            return false;
                        }
                    })
                    .findFirst();

            field.ifPresent(value -> constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(value.getName())
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation());

            return false;
        }
    }
}
