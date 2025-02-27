package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.EmployeeUpdateRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
  Employee toEmployee(EmployeeRequest request);

  EmployeeResponse toEmployeeResponse(Employee employee);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(EmployeeRequest request, @MappingTarget Employee employee);

  Employee toEmployee(EmployeeUpdateRequest request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(EmployeeUpdateRequest request, @MappingTarget Employee employee);

  default Role toRole(com.tsoftware.qtd.constants.EnumType.Role role) {
    return Role.builder().name(role.name()).description(role.getDescription()).build();
  }

  default com.tsoftware.qtd.constants.EnumType.Role toRole(Role role) {
    return com.tsoftware.qtd.constants.EnumType.Role.valueOf(role.getName());
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ProfileRequest request, @MappingTarget Employee employee);
}
