package com.stevenkolamkuzhiyil.SpringCrud.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

public class DependentValidator implements ConstraintValidator<Dependent, Object> {

    private String independentField;
    private String[] fields;
    private String message;

    @Override
    public void initialize(Dependent constraintAnnotation) {
        independentField = constraintAnnotation.independent();
        fields = constraintAnnotation.fields();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object independent = getObjFromField(o, independentField);
        if (independent == null)
            return true;

        Stream<Object> fieldStream = Stream.of(fields)
                .map(fname -> getObjFromField(o, fname));

        boolean valid = fieldStream.noneMatch(Objects::isNull);

        if (!valid) {
            for (String field : fields) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(field)
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }
        }

        return valid;
    }

    private Object getObjFromField(Object object, String fieldName) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(object);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }

        return null;
    }


}
