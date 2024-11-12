package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeCreateRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import java.util.List;

public interface EmployeeService {
  List<EmployeeResponse> getEmployees();

  EmployeeResponse getProfile();

  void createEmployee(EmployeeCreateRequest request);

  void updateProfile(ProfileRequest request);

  void updateEmployee(String userId, EmployeeCreateRequest request);

  void resetPassword(String userId, String newPassword);

  void activeEmployee(String userId);

  void deactivateEmployee(String userId);
}
