package com.tsoftware.qtd.util;

import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;

public class ValidationUtils {
  private ValidationUtils() {}

  public static void validateEqual(Object expected, Object actual) {
    if (!expected.equals(actual)) {
      throw new CommonException(ErrorType.CHECKSUM_INVALID, expected + " != " + actual);
    }
  }
}
