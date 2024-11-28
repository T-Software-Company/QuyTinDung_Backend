package com.tsoftware.qtd.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<IsEnum, String> {

  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(IsEnum constraintAnnotation) {
    this.enumClass = constraintAnnotation.enumClass();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    boolean isValid =
        Arrays.stream(enumClass.getEnumConstants())
            .anyMatch(enumConstant -> enumConstant.name().equals(value));

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              String.format(
                  "Invalid value '%s'. Allowed values are: %s",
                  value, String.join(", ", getEnumConstants())))
          .addConstraintViolation();
    }

    return isValid;
  }

  private String[] getEnumConstants() {
    return Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toArray(String[]::new);
  }
}
