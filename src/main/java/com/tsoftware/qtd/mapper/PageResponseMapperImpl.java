package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageResponseMapperImpl implements PageResponseMapper {

  @Override
  public <T> PageResponse<T> toPageResponse(Page<T> page) {
    return new PageResponse<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        toSort(page.getSort()));
  }
}
