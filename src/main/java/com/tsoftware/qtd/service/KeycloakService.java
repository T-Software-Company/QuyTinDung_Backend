package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import java.util.List;

public interface KeycloakService {
  String createUser(EmployeeRequest employeeRequest);

  void updateUser(ProfileRequest request, String userId);

  void updateUser(EmployeeRequest request, String userId);

  void activeUser(String userId);

  void deactivateUser(String userId);

  void resetPassword(String userId, String newPassword);

  String createGroup(GroupRequest group);

  void updateGroup(GroupRequest group, String kcGroupId);

  void deleteGroup(String kcGroupId);

  void addRolesToGroup(String kcGroupId, List<String> roles);

  void removeRolesOnGroup(String kcGroupId, List<String> roles);

  String[] createClientRoles(Role[] values);

  String createClientRole(Role role);

  void addUserToGroup(String kcGroupId, String userId);

  void removeUserOnGroup(String kcGroupId, String userId);
}
