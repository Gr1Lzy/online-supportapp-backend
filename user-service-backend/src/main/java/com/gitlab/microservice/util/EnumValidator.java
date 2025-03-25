package com.gitlab.microservice.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {
  String message() default "Value is not valid enum";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @SuppressWarnings("rawtypes")
  Class<? extends Enum> enumClass();
}

