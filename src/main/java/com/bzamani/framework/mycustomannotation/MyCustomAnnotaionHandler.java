package com.bzamani.framework.mycustomannotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyCustomAnnotaionHandler implements ConstraintValidator<MyLengthValidator, String> {
    int expectedMin, expectedMax;
    String message;

    @Override
    public void initialize(MyLengthValidator constraintAnnotation) {
        this.expectedMin = constraintAnnotation.minLenght();
        this.expectedMax = constraintAnnotation.maxLength();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() < expectedMin || s.length() > expectedMax) {
            System.out.println(message);
            return false;
        }
        else
            return true;
    }

   /* public static void checkLength(Object object) throws Exception {
        String exceptinMessage = "";
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(MyLengthValidator.class)) {
                int expectedMin = field.getAnnotation(MyLengthValidator.class).minLenght();
                int expectedMax = field.getAnnotation(MyLengthValidator.class).maxLength();
                int actual = field.get(object).toString().length();
                if (actual < expectedMin || actual > expectedMax)
                    exceptinMessage += "\n field *" + field.getName() + "* expected size is between " + expectedMin + " and " + expectedMax + " but got " + actual + " for input string '" +field.get(object).toString()+"'." ;
            }
        }
        System.out.println(exceptinMessage);
        if (exceptinMessage.length() > 0)
            throw new Exception(exceptinMessage);
    }*/

}
