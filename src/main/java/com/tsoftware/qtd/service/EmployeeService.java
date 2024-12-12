package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.EmployeeUpdateRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.entity.Employee;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface EmployeeService {

  EmployeeResponse getProfile();

  EmployeeResponse createEmployee(EmployeeRequest request);

  EmployeeResponse updateProfile(ProfileRequest request);

  EmployeeResponse updateEmployee(UUID id, EmployeeUpdateRequest request);

  void resetPassword(UUID id);

  void enable(UUID id);

  void disable(UUID id);

  EmployeeResponse getEmployee(UUID id);

  Page<EmployeeResponse> getAll(Specification<Employee> spec, Pageable page);

  void addRoles(UUID id, List<String> roles);

  void removeRoles(UUID id, List<String> roles);

  void disables(List<UUID> ids);

  void enables(List<UUID> ids);

  void delete(UUID id);

  void delete(List<UUID> ids);
}
