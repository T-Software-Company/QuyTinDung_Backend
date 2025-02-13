package com.tsoftware.qtd.commonlib.util;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

  /**
   * Finds and returns the first element in the list that satisfies the condition.
   *
   * @param <T> The type of elements in the list.
   * @param list The list to search.
   * @param condition The condition to check for each element.
   * @return The first element that satisfies the condition, or Optional.empty() if not found.
   */
  public static <T> Optional<T> findFirst(Iterable<T> list, Predicate<T> condition) {
    if (list == null || condition == null) {
      return Optional.empty();
    }

    for (T element : list) {
      if (condition.test(element)) {
        return Optional.of(element);
      }
    }

    return Optional.empty();
  }
}
