package com.tsoftware.qtd.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UUIDValidator.class})
public @interface IsUUID {
  String message() default "Invalid UUID format, id must be a UUID";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
