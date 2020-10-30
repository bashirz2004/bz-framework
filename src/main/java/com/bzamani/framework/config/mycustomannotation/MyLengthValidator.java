package com.bzamani.framework.config.mycustomannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {MyCustomAnnotaionHandler.class})
public @interface MyLengthValidator {
    String message() default  "Error. length is not valid !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    public int minLenght() default 0;
    public int maxLength() default 0;
}
