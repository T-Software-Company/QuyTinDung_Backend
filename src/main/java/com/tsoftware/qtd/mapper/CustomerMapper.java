package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.CustomerDto;
import com.tsoftware.qtd.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
  Customer toEntity(CustomerDto dto);

  CustomerDto toDto(Customer entity);

  void updateEntity(CustomerDto dto, @MappingTarget Customer entity);
}
