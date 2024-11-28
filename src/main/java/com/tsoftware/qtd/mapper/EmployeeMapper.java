package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface EmployeeMapper {
  @Mapping(source = "address", target = "address")
  Employee toEmployee(EmployeeRequest request);

  @Mapping(source = "address", target = "address")
  EmployeeResponse toEmployeeResponse(Employee employee);

  @Mapping(source = "address", target = "address")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(EmployeeRequest request, @MappingTarget Employee employee);

  default Role toRole(com.tsoftware.qtd.constants.EnumType.Role role) {
    return Role.builder().name(role.name()).description(role.getDescription()).build();
  }

  default com.tsoftware.qtd.constants.EnumType.Role toRole(Role role) {
    return com.tsoftware.qtd.constants.EnumType.Role.valueOf(role.getName());
  }
}
