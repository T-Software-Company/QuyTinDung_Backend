package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.address.AddressVm;
import com.tsoftware.qtd.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  Address toAddress(AddressVm address);

  AddressVm toAddressVm(Address address);
}
