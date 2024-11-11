package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.EmployeeCreateRequest;
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
  Employee toEmployee(EmployeeCreateRequest request);

  @Mapping(target = "roles", ignore = true)
  @Mapping(source = "address", target = "address")
  EmployeeResponse toEmployeeResponse(Employee employee);

  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "address", target = "address")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateProfileFromRequest(EmployeeCreateRequest request, @MappingTarget Employee employee);
}
