package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.address.AddressDTO;
import com.tsoftware.qtd.entity.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  Address toAddress(AddressDTO address);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  AddressDTO toAddressVm(Address address);
}
