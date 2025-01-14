package com.tsoftware.qtd.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {
  Class<? extends JpaRepository<?, ?>> repositoryClass;
  @Autowired private ApplicationContext applicationContext;
  private String idFieldName;
  private String[] fields;

  @Override
  public void initialize(Unique constraintAnnotation) {
    repositoryClass = constraintAnnotation.repositoryClass();
    this.idFieldName = constraintAnnotation.idFieldName();
    this.fields = constraintAnnotation.fields();
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    if (object == null) {
      return true;
    }
    try {
      var idField = object.getClass().getDeclaredField(this.idFieldName);
      idField.setAccessible(true);
      var requestId = idField.get(object);
      JpaRepository<?, ?> repository = applicationContext.getBean(this.repositoryClass);
      Map<String, String> messages = new HashMap<>();
      for (String uniqueFieldName : this.fields) {
        var checkMethod =
            "findBy" + uniqueFieldName.substring(0, 1).toUpperCase() + uniqueFieldName.substring(1);
        Method method = repositoryClass.getMethod(checkMethod, String.class);
        method.setAccessible(true);
        var field = object.getClass().getDeclaredField(uniqueFieldName);
        field.setAccessible(true);
        var value = field.get(object);
        var optional = (Optional<?>) method.invoke(repository, value);

        if (requestId == null && optional.isPresent()) {
          messages.put(
              uniqueFieldName,
              "The value '" + value + "' is already in use. Please choose another.");
        }
        if (requestId != null
            && optional.isPresent()
            && !optional
                .get()
                .getClass()
                .getSuperclass()
                .getDeclaredField(this.idFieldName)
                .get(optional.get())
                .equals(requestId)) {
          messages.put(
              uniqueFieldName,
              "The value '" + value + "' is already in use. Please choose another.");
        }
      }
      if (!messages.isEmpty()) {
        context.disableDefaultConstraintViolation();
        for (Map.Entry<String, String> entry : messages.entrySet()) {
          context
              .buildConstraintViolationWithTemplate(entry.getValue())
              .addPropertyNode(entry.getKey())
              .addConstraintViolation();
        }
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error validating unique constraint", e);
    }
  }
}
