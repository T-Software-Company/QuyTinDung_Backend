package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.EmployeeCreateRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;

public interface KeycloakService {
  public void createUser(EmployeeCreateRequest employeeCreateRequest);

  void updateUser(ProfileRequest request, String userId);

  void updateUser(EmployeeCreateRequest request, String userId);
}
