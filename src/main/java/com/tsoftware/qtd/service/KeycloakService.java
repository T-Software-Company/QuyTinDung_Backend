package com.tsoftware.qtd.service;

import com.tsoftware.qtd.configuration.IdpProperties;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.exception.*;
import com.tsoftware.qtd.kcTransactionManager.KcTransactionContext;
import com.tsoftware.qtd.kcTransactionManager.KcTransactional;
import jakarta.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeycloakService {
  private final Keycloak keycloak;
  private final IdpProperties idpProperties;
  private final RealmResource realmResource;

  public KeycloakService(Keycloak keycloak, IdpProperties idpProperties) {
    this.keycloak = keycloak;
    this.idpProperties = idpProperties;
    this.realmResource = keycloak.realm(idpProperties.getRealm());
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.CREATE_USER)
  public String createUser(EmployeeRequest employeeRequest) {

    var user = getUserRepresentation(employeeRequest);
    var res = realmResource.users().create(user);
    if (res.getStatus() != 201) {
      throw new KeycloakException(res.getStatus(), extractErrorMessage(res));
    }
    String userId = null;
    try {
      userId = res.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      var roles = employeeRequest.getRoles();
      var clientRoles = getClientRoles(roles);
      realmResource.users().get(userId).roles().clientLevel(getClientUUID()).add(clientRoles);
      String finalUserId = userId;
      getGroupsBy(employeeRequest.getGroups())
          .forEach(
              group -> {
                realmResource.users().get(finalUserId).joinGroup(group.getId());
              });
      return userId;
    } catch (Exception e) {
      if (userId != null) {
        deleteUser(userId);
      }
      throw e;
    }
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.CREATE_USER)
  public String createUser(CustomerRequest customerRequest) {
    var user = getUserRepresentation(customerRequest);
    var res = realmResource.users().create(user);
    if (res.getStatus() != 201) {
      throw new KeycloakException(res.getStatus(), extractErrorMessage(res));
    }
    String userId = null;
    try {
      userId = res.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      var customerRole = getClientRoles(List.of(Role.CUSTOMER.name()));
      realmResource.users().get(userId).roles().clientLevel(getClientUUID()).add(customerRole);
      return userId;
    } catch (Exception e) {
      if (userId != null) {
        deleteUser(userId);
      }
      throw e;
    }
  }

  public void updateUser(ProfileRequest request, String userId) {
    var user = realmResource.users().get(userId).toRepresentation();
    if (user == null) {
      throw new NotFoundException("Employee not found");
    }
    if (request.getFirstName() != null) {
      user.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null) {
      user.setLastName(request.getLastName());
    }
    if (request.getEmail() != null) {
      user.setEmail(request.getEmail());
    }
    realmResource.users().get(userId).update(user);
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.UPDATE_USER)
  public void updateUser(EmployeeUpdateRequest request, String userId) {
    var userResource = realmResource.users().get(userId);
    var userRepresentation = userResource.toRepresentation();

    if (request.getUsername() != null) {
      userRepresentation.setUsername(request.getUsername());
    }
    if (request.getEmail() != null) {
      userRepresentation.setEmail(request.getEmail());
      userRepresentation.setEmailVerified(false);
    }
    if (request.getFirstName() != null) {
      userRepresentation.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null) {
      userRepresentation.setLastName(request.getLastName());
    }

    userRepresentation.setEnabled(true);
    userResource.update(userRepresentation);

    removeRolesOnUser(userId);
    var clientRoles = getClientRoles(request.getRoles());
    realmResource.users().get(userId).roles().clientLevel(getClientUUID()).add(clientRoles);
    getGroupsBy(request.getGroups())
        .forEach(
            group -> {
              realmResource.users().get(userId).joinGroup(group.getId());
            });
  }

  public void activeUser(String userId) {
    var usersResource = keycloak.realm(idpProperties.getRealm()).users();
    var userResource = usersResource.get(userId);
    var userRepresentation = userResource.toRepresentation();
    userRepresentation.setEnabled(true);
    userResource.update(userRepresentation);
  }

  public void deactivateUser(String userId) {
    var usersResource = keycloak.realm(idpProperties.getRealm()).users();
    var userResource = usersResource.get(userId);
    var userRepresentation = userResource.toRepresentation();
    userRepresentation.setEnabled(false);
    userResource.update(userRepresentation);
  }

  public void resetPassword(String userId, String newPassword) {

    var usersResource = keycloak.realm(idpProperties.getRealm()).users();
    var userResource = usersResource.get(userId);
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(newPassword);
    credential.setTemporary(false);

    userResource.resetPassword(credential);
  }

  public void resetPasswordByEmail(String userId) {
    realmResource
        .users()
        .get(userId)
        .executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.CREATE_GROUP)
  public String createGroup(GroupRequest group) {

    GroupRepresentation groupRepresentation = new GroupRepresentation();
    groupRepresentation.setName(group.getName());
    var groupResource = realmResource.groups();
    var response = groupResource.add(groupRepresentation);
    if (response.getStatus() != 201) {
      throw new KeycloakException(response.getStatus(), extractErrorMessage(response));
    }
    String groupId = null;
    try {
      groupId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      groupResource
          .group(groupId)
          .roles()
          .clientLevel(getClientUUID())
          .add(getClientRoles(group.getRoles()));
      return groupId;
    } catch (Exception e) {
      if (groupId != null) {
        deleteGroup(groupId);
      }
      throw e;
    }
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.UPDATE_GROUP)
  public void updateGroup(GroupRequest group, String kcGroupId) {
    var groupResource = realmResource.groups().group(kcGroupId);
    var groupRepresentation = groupResource.toRepresentation();
    groupRepresentation.setName(group.getName());
    groupResource.update(groupRepresentation);
    removeAllRolesOnGroup(kcGroupId);
    groupResource.roles().clientLevel(getClientUUID()).add(getClientRoles(group.getRoles()));
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.DELETE_GROUP)
  public void deleteGroup(String kcGroupId) {
    var groupResource = realmResource.groups().group(kcGroupId);
    groupResource.remove();
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.ADD_ROLE_TO_GROUP)
  public void addRolesToGroup(String kcGroupId, List<String> roles) {
    var groupResource = realmResource.groups().group(kcGroupId);
    groupResource.roles().clientLevel(getClientUUID()).add(getClientRoles(roles));
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.REMOVE_ROLE_ON_GROUP)
  public void removeRolesOnGroup(String kcGroupId, List<String> roles) {
    var groupResource = realmResource.groups().group(kcGroupId);
    groupResource.roles().clientLevel(getClientUUID()).remove(getClientRoles(roles));
  }

  public String[] createClientRoles(Role[] values) {
    return Arrays.stream(values).map(this::createClientRole).toArray(String[]::new);
  }

  public String createClientRole(Role role) {
    var clientResource = realmResource.clients().get(getClientUUID());
    var clientRolesResource = clientResource.roles();
    try {
      var roleRepresentationExists = clientRolesResource.get(role.name()).toRepresentation();
      return roleRepresentationExists.getId();
    } catch (jakarta.ws.rs.NotFoundException e) {
      RoleRepresentation roleRepresentation = new RoleRepresentation();
      roleRepresentation.setName(role.name());
      roleRepresentation.setDescription(role.getDescription());
      clientRolesResource.create(roleRepresentation);
      return clientRolesResource.get(role.name()).toRepresentation().getId();
    }
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.ADD_USER_TO_GROUP)
  public void addUserToGroup(String kcGroupId, String userId) {
    realmResource.users().get(userId).joinGroup(kcGroupId);
  }

  public void addUserToGroup(String kcGroupId, List<String> ids) {
    ids.forEach(
        id -> {
          realmResource.users().get(id).joinGroup(kcGroupId);
        });
  }

  public void removeUserOnGroup(String kcGroupId, List<String> ids) {
    ids.forEach(id -> realmResource.users().get(id).leaveGroup(kcGroupId));
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.REMOVE_USER_ON_GROUP)
  public void removeUserOnGroup(String kcGroupId, String userId) {
    realmResource.users().get(userId).leaveGroup(kcGroupId);
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.ADD_ROLE_TO_USER)
  public void addRolesToUser(String userId, List<String> roles) {
    var userResource = realmResource.users().get(userId);
    userResource.roles().clientLevel(getClientUUID()).add(getClientRoles(roles));
  }

  @KcTransactionContext(KcTransactional.KcTransactionType.REMOVE_ROLE_ON_USER)
  public void removeRolesOnUser(String userId, List<String> roles) {
    var userResource = realmResource.users().get(userId);
    userResource.roles().clientLevel(getClientUUID()).remove(getClientRoles(roles));
  }

  public UserRepresentation getUser(String userId) {
    var userResource = realmResource.users().get(userId);
    return userResource.toRepresentation();
  }

  public GroupRepresentation getGroup(String id) {
    return realmResource.groups().group(id).toRepresentation();
  }

  public List<UserRepresentation> getUsersByGroup(String id) {
    return realmResource.groups().group(id).members();
  }

  public void deleteUser(String id) {
    var userResource = realmResource.users().get(id);
    userResource.remove();
  }

  public List<GroupRepresentation> getGroupsByUser(String id) {
    return realmResource.users().get(id).groups();
  }

  public void updateUser(
      UserRepresentation userRepresentation,
      List<RoleRepresentation> roles,
      List<GroupRepresentation> groups) {
    realmResource.users().get(userRepresentation.getId()).update(userRepresentation);
    removeRolesOnUser(userRepresentation.getId());
    realmResource
        .users()
        .get(userRepresentation.getId())
        .roles()
        .clientLevel(getClientUUID())
        .add(roles);
    realmResource.users().get(userRepresentation.getId()).groups().addAll(groups);
  }

  public void resetRoles(
      UserRepresentation userRepresentation, List<RoleRepresentation> roleRepresentations) {
    removeRolesOnUser(userRepresentation.getId());
    realmResource
        .users()
        .get(userRepresentation.getId())
        .roles()
        .clientLevel(getClientUUID())
        .add(roleRepresentations);
  }

  public List<RoleRepresentation> getRolesByGroup(String id) {
    return realmResource.groups().group(id).roles().clientLevel(getClientUUID()).listAll();
  }

  public void updateGroup(
      GroupRepresentation groupRepresentation, List<RoleRepresentation> roleRepresentations) {
    realmResource.groups().group(groupRepresentation.getId()).update(groupRepresentation);
    realmResource
        .groups()
        .group(groupRepresentation.getId())
        .roles()
        .clientLevel(getClientUUID())
        .add(roleRepresentations);
  }

  public void createGroup(
      GroupRepresentation groupRepresentation,
      List<RoleRepresentation> roleRepresentations,
      List<UserRepresentation> userRepresentations) {
    var groupResource = realmResource.groups();
    var response = groupResource.add(groupRepresentation);
    if (response.getStatus() != 201) {
      throw new KeycloakException(response.getStatus(), extractErrorMessage(response));
    }

    var groupId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    groupResource.group(groupId).roles().clientLevel(getClientUUID()).add(roleRepresentations);
    groupResource.group(groupId).members().addAll(userRepresentations);
  }

  public void resetRoles(
      GroupRepresentation groupRepresentation, List<RoleRepresentation> roleRepresentations) {
    removeAllRolesOnGroup(groupRepresentation.getId());
    realmResource
        .groups()
        .group(groupRepresentation.getId())
        .roles()
        .clientLevel(getClientUUID())
        .add(roleRepresentations);
  }

  public List<RoleRepresentation> getRolesByUser(String id) {
    return realmResource.users().get(id).roles().clientLevel(getClientUUID()).listAll();
  }

  private String extractErrorMessage(Response response) {
    var map = response.readEntity(Map.class);
    return (String) map.get("errorMessage");
  }

  private static UserRepresentation getUserRepresentation(EmployeeRequest employeeRequest) {
    var user = new UserRepresentation();
    user.setUsername(employeeRequest.getUsername());
    user.setEmail(employeeRequest.getEmail());
    user.setFirstName(employeeRequest.getFirstName());
    user.setLastName(employeeRequest.getLastName());
    user.setEnabled(true);
    user.setEmailVerified(false);
    var credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setTemporary(true);
    credential.setValue(employeeRequest.getPassword());
    user.setCredentials(List.of(credential));
    return user;
  }

  private static UserRepresentation getUserRepresentation(CustomerRequest customerRequest) {
    var user = new UserRepresentation();
    user.setUsername(customerRequest.getUsername());
    user.setEmail(customerRequest.getEmail());
    user.setFirstName(customerRequest.getFirstName());
    user.setLastName(customerRequest.getLastName());
    user.setEnabled(true);
    user.setEmailVerified(false);
    var credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setTemporary(true);
    credential.setValue(customerRequest.getPassword());
    user.setCredentials(List.of(credential));
    return user;
  }

  private void removeRolesOnUser(String userId) {
    var userResource = realmResource.users().get(userId);
    var clientRoles = userResource.roles().clientLevel(getClientUUID()).listAll();
    if (!clientRoles.isEmpty()) {
      userResource.roles().clientLevel(getClientUUID()).remove(clientRoles);
    }
  }

  private void removeAllRolesOnGroup(String groupId) {
    var groupResource = realmResource.groups().group(groupId);
    var clientRoles = groupResource.roles().clientLevel(getClientUUID()).listAll();
    if (!clientRoles.isEmpty()) {
      groupResource.roles().clientLevel(getClientUUID()).remove(clientRoles);
    }
  }

  private List<RoleRepresentation> getClientRoles(List<String> roles) {
    if (roles == null) return Collections.emptyList();
    var clientResource = realmResource.clients().get(getClientUUID());
    return roles.stream()
        .map(
            role -> {
              try {
                return clientResource.roles().get(role).toRepresentation();
              } catch (jakarta.ws.rs.NotFoundException e) {
                throw new NotFoundException("Role " + role + " not found");
              }
            })
        .toList();
  }

  private String getClientUUID() {
    var clientRepresentations = realmResource.clients().findByClientId(idpProperties.getClientId());
    return clientRepresentations.getFirst().getId();
  }

  private List<GroupRepresentation> getGroupsByIds(List<String> ids) {
    return ids.stream()
        .map(
            id -> {
              try {
                return realmResource.groups().group(id).toRepresentation();
              } catch (jakarta.ws.rs.NotFoundException e) {
                throw new CommonException(ErrorType.ENTITY_NOT_FOUND, "Group: " + id);
              }
            })
        .collect(Collectors.toList());
  }

  private List<GroupRepresentation> getGroupsBy(List<GroupDTO> groups) {
    if (groups == null) return Collections.emptyList();
    return getGroupsByIds(groups.stream().map(GroupDTO::getKcGroupId).collect(Collectors.toList()));
  }
}
