package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.CustomerDTO;
import com.tsoftware.qtd.entity.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
  Customer toEntity(CustomerDTO dto);

  CustomerDTO toDTO(Customer entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CustomerDTO dto, @MappingTarget Customer entity);
}
