package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = {CMNDMapper.class, CCCDMapper.class, PassPortMapper.class})
public interface CustomerMapper {
  Customer toEntity(CustomerRequest dto);

  CustomerResponse toResponse(Customer entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CustomerRequest dto, @MappingTarget Customer entity);
}
