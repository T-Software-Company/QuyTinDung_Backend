package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface EmployeeService {
  Page<EmployeeResponse> getEmployees(Pageable pageable);

  EmployeeResponse getProfile();

  EmployeeResponse createEmployee(EmployeeRequest request);

  EmployeeResponse updateProfile(ProfileRequest request);

  EmployeeResponse updateEmployee(String userId, EmployeeRequest request);

  void resetPassword(String userId, String newPassword);

  void activeEmployee(String userId);

  void deactivateEmployee(String userId);

  EmployeeResponse getEmployee(Long id);

  Page<EmployeeResponse> getAll(Specification<Employee> spec, Pageable page);
}
