package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.AdminRequest;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import java.util.List;

public interface EmployeeService {
  List<EmployeeResponse> getAllProfiles();

  EmployeeResponse getMyProfile();

  EmployeeResponse registerProfile(AdminRequest request);

  void updateProfile(EmployeeRequest request);

  void updateProfileByAdmin(String userId, AdminRequest request);

  void resetPassword(String userId, String newPassword);

  void activateUser(String userId);

  void deactivateUser(String userId);
}
