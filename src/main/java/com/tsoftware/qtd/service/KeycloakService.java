package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.customer.CustomerDTO;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.EmployeeUpdateRequest;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import java.util.List;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
  String createUser(EmployeeRequest employeeRequest);

  String createUser(CustomerDTO customerDTO);

  void updateUser(ProfileRequest request, String userId);

  void updateUser(EmployeeUpdateRequest request, String userId);

  void activeUser(String userId);

  void deactivateUser(String userId);

  void resetPassword(String userId, String newPassword);

  void resetPasswordByEmail(String userId);

  String createGroup(GroupRequest group);

  void updateGroup(GroupRequest group, String kcGroupId);

  void deleteGroup(String kcGroupId);

  void addRolesToGroup(String kcGroupId, List<String> roles);

  void removeRolesOnGroup(String kcGroupId, List<String> roles);

  String[] createClientRoles(Role[] values);

  String createClientRole(Role role);

  void addUserToGroup(String kcGroupId, String userId);

  void removeUserOnGroup(String kcGroupId, String userId);

  void addRolesToUser(String userId, List<String> roles);

  void removeRolesOnUser(String userId, List<String> roles);

  UserRepresentation getUser(String userId);

  GroupRepresentation getGroup(String id);

  List<UserRepresentation> getUsersByGroup(String id);

  void deleteUser(String id);

  List<GroupRepresentation> getGroupsByUser(String id);

  List<RoleRepresentation> getRolesByGroup(String id);

  void updateGroup(GroupRepresentation root, List<RoleRepresentation> includes);

  void createGroup(
      GroupRepresentation root, List<RoleRepresentation> includes, List<UserRepresentation> object);

  void resetRoles(
      GroupRepresentation groupRepresentation, List<RoleRepresentation> roleRepresentations);

  List<RoleRepresentation> getRolesByUser(String id);

  void updateUser(
      UserRepresentation userRepresentation,
      List<RoleRepresentation> roleRepresentations,
      List<GroupRepresentation> groupRepresentations);

  void resetRoles(
      UserRepresentation userRepresentation, List<RoleRepresentation> roleRepresentations);

  void addUserToGroup(String kcGroupId, List<String> ids);

  void removeUserOnGroup(String kcGroupId, List<String> ids);
}
