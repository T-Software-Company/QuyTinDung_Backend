package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.PageResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface PageResponseMapper {

  <T> PageResponse<T> toPageResponse(Page<T> page);

  default List<PageResponse.Sort> toSort(Sort sort) {
    return sort.stream()
        .map(o -> new PageResponse.Sort(o.getDirection().name(), o.getProperty()))
        .collect(Collectors.toList());
  }
}
