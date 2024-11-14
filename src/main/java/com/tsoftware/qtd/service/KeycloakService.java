package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;

public interface KeycloakService {
  public String createUser(EmployeeRequest employeeRequest);

  void updateUser(ProfileRequest request, String userId);

  void updateUser(EmployeeRequest request, String userId);

  void activeUser(String userId);

  void deactivateUser(String userId);

  void resetPassword(String userId, String newPassword);
}
