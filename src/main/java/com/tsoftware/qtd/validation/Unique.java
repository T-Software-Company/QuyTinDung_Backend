package com.tsoftware.qtd.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {
  String message() default "Value is already unique";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends JpaRepository<?, ?>> repositoryClass();

  String[] fields();

  String idFieldName() default "id";
}
