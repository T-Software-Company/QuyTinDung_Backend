package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {CMNDMapper.class, CCCDMapper.class, PassPortMapper.class})
public interface CustomerMapper {
  @Mapping(source = "cmnd", target = "cmnd")
  @Mapping(source = "cccd", target = "cccd")
  @Mapping(source = "passPort", target = "passPort")
  Customer toEntity(CustomerRequest dto);

  @Mapping(source = "cmnd", target = "cmnd")
  @Mapping(source = "cccd", target = "cccd")
  @Mapping(source = "passPort", target = "passPort")
  CustomerResponse toResponse(Customer entity);

  void updateEntity(CustomerRequest dto, @MappingTarget Customer entity);
}
