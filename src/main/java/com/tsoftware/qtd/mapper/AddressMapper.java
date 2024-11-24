package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.entity.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  Address toAddress(AddressDto address);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  AddressDto toAddressVm(Address address);
}
