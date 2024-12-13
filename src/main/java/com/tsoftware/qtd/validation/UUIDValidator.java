package com.tsoftware.qtd.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<IsUUID, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) return true;
    try {
      UUID.fromString(value);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
