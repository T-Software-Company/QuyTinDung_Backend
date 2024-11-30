package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.entity.Employee;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface EmployeeService {

  EmployeeResponse getProfile();

  EmployeeResponse createEmployee(EmployeeRequest request);

  EmployeeResponse updateProfile(ProfileRequest request);

  EmployeeResponse updateEmployee(String userId, EmployeeRequest request);

  void resetPassword(String userId, String newPassword);

  void enable(Long id);

  void disable(Long id);

  EmployeeResponse getEmployee(Long id);

  Page<EmployeeResponse> getAll(Specification<Employee> spec, Pageable page);

  void addRoles(Long id, List<String> roles);

  void removeRoles(Long id, List<String> roles);

  void disables(List<Long> ids);

  void enables(List<Long> ids);
}
