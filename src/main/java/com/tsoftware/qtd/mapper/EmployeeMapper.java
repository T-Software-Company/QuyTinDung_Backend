package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.entity.Employee;
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
}
