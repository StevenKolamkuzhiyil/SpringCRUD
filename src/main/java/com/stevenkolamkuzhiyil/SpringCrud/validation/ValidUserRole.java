package com.stevenkolamkuzhiyil.SpringCrud.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidUserRoleValidator.class)
public @interface ValidUserRole {

    String message() default "Invalid User role";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.FIELD)
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidUserRole[] value();
    }

}
