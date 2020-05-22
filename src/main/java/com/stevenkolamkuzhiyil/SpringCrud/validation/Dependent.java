package com.stevenkolamkuzhiyil.SpringCrud.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DependentValidator.class)
public @interface Dependent {
    String message() default "Field must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String independent();

    String[] fields() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Dependent[] value();
    }
}
