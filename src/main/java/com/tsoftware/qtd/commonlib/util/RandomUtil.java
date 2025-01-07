package com.tsoftware.qtd.commonlib.util;

import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtil {
  private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final Random random = new Random();

  public static String randomAlphaNumeric(int count) {
    StringBuilder builder = new StringBuilder();
    while (count-- > 0) {
      int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
  }

  public static String randomAlpha(int count) {
    StringBuilder builder = new StringBuilder();
    while (count-- > 0) {
      int character = random.nextInt(ALPHA_STRING.length());
      builder.append(ALPHA_STRING.charAt(character));
    }
    return builder.toString();
  }

  public static String randomWithFormat(String format) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < format.length(); i++) {
      if (format.charAt(i) == '#') {
        str.append(randomAlphaNumeric(1));
      } else if (format.charAt(i) == '*') {
        str.append(randomInt(0, 9));
      } else {
        str.append(format.charAt(i));
      }
    }
    return str.toString();
  }

  public static Integer randomInt(int min, int max) {
    return random.nextInt(max - min) + min;
  }
}
