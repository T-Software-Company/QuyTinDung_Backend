package com.tsoftware.qtd.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
  private List<T> content;
  private int page;
  private int size;
  private long totalElements;
  private int totalPages;
  private List<Sort> sorts;

  @AllArgsConstructor
  @Getter
  @Setter
  public static class Sort {
    private String direction;
    private String property;
  }
}
