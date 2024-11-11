package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.AdminRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface EmployeeMapper {
  @Mapping(target = "userId", ignore = true)
  @Mapping(source = "address", target = "address")
  Employee toProfile(AdminRequest request);

  @Mapping(target = "roles", ignore = true)
  @Mapping(source = "address", target = "address")
  EmployeeResponse toProfileResponse(Employee employee);

  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "address", target = "address")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateProfileFromRequest(AdminRequest request, @MappingTarget Employee employee);
}
