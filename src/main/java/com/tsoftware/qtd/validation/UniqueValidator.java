package com.tsoftware.qtd.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
  Class<? extends JpaRepository<?, ?>> repositoryClass;
  String checkMethod;
  @Autowired private ApplicationContext applicationContext;

  @Override
  public void initialize(Unique constraintAnnotation) {
    repositoryClass = constraintAnnotation.repositoryClass();
    checkMethod = constraintAnnotation.checkMethod();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true;
    }
    try {
      JpaRepository<?, ?> repository = applicationContext.getBean(this.repositoryClass);
      Method method = repositoryClass.getMethod(checkMethod, String.class);
      Boolean exists = (Boolean) method.invoke(repository, value);

      if (exists) {
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(
                "The value '" + value + "' is already in use. Please choose another.")
            .addConstraintViolation();
        return false;
      }
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error validating unique constraint", e);
    }
  }
}
