package com.tsoftware.qtd.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ListEnumValidator implements ConstraintValidator<IsEnum, List<String>> {

  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(IsEnum constraintAnnotation) {
    this.enumClass = constraintAnnotation.enumClass();
  }

  @Override
  public boolean isValid(List<String> value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true;
    }

    boolean isValid =
        value.stream()
            .allMatch(
                item ->
                    Arrays.stream(enumClass.getEnumConstants())
                        .anyMatch(enumConstant -> enumConstant.name().equals(item)));

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              String.format(
                  "Invalid values. Allowed values are: %s", String.join(", ", getEnumConstants())))
          .addConstraintViolation();
    }
    return isValid;
  }

  private String[] getEnumConstants() {
    return Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toArray(String[]::new);
  }
}
