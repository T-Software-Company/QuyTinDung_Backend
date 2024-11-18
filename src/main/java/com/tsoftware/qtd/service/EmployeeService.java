package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import java.util.List;

public interface EmployeeService {
  List<EmployeeResponse> getEmployees();

  EmployeeResponse getProfile();

  EmployeeResponse createEmployee(EmployeeRequest request);

  EmployeeResponse updateProfile(ProfileRequest request);

  EmployeeResponse updateEmployee(String userId, EmployeeRequest request);

  void resetPassword(String userId, String newPassword);

  void activeEmployee(String userId);

  void deactivateEmployee(String userId);
}
