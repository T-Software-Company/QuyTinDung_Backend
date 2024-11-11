package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  Address toAddress(AddressDto address);

  AddressDto toAddressVm(Address address);
}
