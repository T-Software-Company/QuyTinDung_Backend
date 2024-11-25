package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.configuration.IdpProperties;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.exception.KeycloakException;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeycloakServiceIml implements KeycloakService {
  private final Keycloak keycloak;
  private final IdpProperties idpProperties;
  private final RealmResource realmResource;

  public KeycloakServiceIml(Keycloak keycloak, IdpProperties idpProperties) {
    this.keycloak = keycloak;
    this.idpProperties = idpProperties;
    this.realmResource = keycloak.realm(idpProperties.getRealm());
  }

  @Override
  public String createUser(EmployeeRequest employeeRequest) {

    String userId = null;
    var user = getUserRepresentation(employeeRequest);
    var res = realmResource.users().create(user);
    if (res.getStatus() != 201) {
      throw new KeycloakException(res.getStatus(), extractErrorMessage(res));
    }

    try {
      userId = res.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      var roles = employeeRequest.getRoles();
      var clientRoles = getClientRoles(roles);
      realmResource.users().get(userId).roles().clientLevel(getClientIdOnDb()).add(clientRoles);
      return userId;
    } catch (RuntimeException e) {
      if (userId != null) {
        realmResource.users().delete(userId);
      }
      throw e;
    }
  }

  @Override
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

  @Override
  public void updateUser(EmployeeRequest request, String userId) {
    var userResource = realmResource.users().get(userId);
    var userRepresentation = userResource.toRepresentation();

    if (request.getUsername() != null) {
      userRepresentation.setUsername(request.getUsername());
    }
    if (request.getEmail() != null) {
      userRepresentation.setEmail(request.getEmail());
    }
    if (request.getFirstName() != null) {
      userRepresentation.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null) {
      userRepresentation.setLastName(request.getLastName());
    }

    userRepresentation.setEnabled(true);
    userRepresentation.setEmailVerified(false);

    if (request.getPassword() != null) {
      var credential = new CredentialRepresentation();
      credential.setType(CredentialRepresentation.PASSWORD);
      credential.setTemporary(false);
      credential.setValue(request.getPassword());
      userRepresentation.setCredentials(List.of(credential));
    }
    userResource.update(userRepresentation);

    removeRolesOnUser(userId);
    var clientRoles = getClientRoles(request.getRoles());
    realmResource.users().get(userId).roles().clientLevel(getClientIdOnDb()).add(clientRoles);
  }

  @Override
  public void activeUser(String userId) {
    var usersResource = keycloak.realm(idpProperties.getRealm()).users();
    var userResource = usersResource.get(userId);
    var userRepresentation = userResource.toRepresentation();
    userRepresentation.setEnabled(true);
    userResource.update(userRepresentation);
  }

  @Override
  public void deactivateUser(String userId) {
    var usersResource = keycloak.realm(idpProperties.getRealm()).users();
    var userResource = usersResource.get(userId);
    var userRepresentation = userResource.toRepresentation();
    userRepresentation.setEnabled(false);
    userResource.update(userRepresentation);
  }

  @Override
  public void resetPassword(String userId, String newPassword) {
    var usersResource = keycloak.realm(idpProperties.getRealm()).users();
    var userResource = usersResource.get(userId);

    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(newPassword);
    credential.setTemporary(false);

    userResource.resetPassword(credential);
  }

  @Override
  public String createGroup(GroupRequest group) {

    GroupRepresentation groupRepresentation = new GroupRepresentation();
    groupRepresentation.setName(group.getName());
    String groupId = null;
    var groupResource = realmResource.groups();
    var response = groupResource.add(groupRepresentation);
    if (response.getStatus() != 201) {
      throw new KeycloakException(response.getStatus(), extractErrorMessage(response));
    }
    try {

      groupId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      groupResource
          .group(groupId)
          .roles()
          .clientLevel(getClientIdOnDb())
          .add(getClientRoles(group.getRoles()));
      return groupId;

    } catch (Exception e) {
      if (groupId != null) {
        realmResource.groups().group(groupId).remove();
      }
      throw e;
    }
  }

  @Override
  public void updateGroup(GroupRequest group, String kcGroupId) {
    var groupResource = realmResource.groups().group(kcGroupId);
    var groupRepresentation = groupResource.toRepresentation();
    groupRepresentation.setName(group.getName());
    groupResource.update(groupRepresentation);
    removeAllRolesOnGroup(kcGroupId);
    groupResource.roles().clientLevel(getClientIdOnDb()).add(getClientRoles(group.getRoles()));
  }

  @Override
  public void deleteGroup(String kcGroupId) {
    var groupResource = realmResource.groups().group(kcGroupId);
    groupResource.remove();
  }

  @Override
  public void addRolesToGroup(String kcGroupId, List<Role> roles) {
    var groupResource = realmResource.groups().group(kcGroupId);
    groupResource.roles().clientLevel(getClientIdOnDb()).add(getClientRoles(roles));
  }

  @Override
  public void removeRolesOnGroup(String kcGroupId, List<Role> roles) {
    var groupResource = realmResource.groups().group(kcGroupId);
    groupResource.roles().clientLevel(getClientIdOnDb()).remove(getClientRoles(roles));
  }

  @Override
  public void createClientRoles(Role[] values) {
    var clientResource = realmResource.clients().get(getClientIdOnDb());
    var clientRolesResource = clientResource.roles();
    for (Role role : values) {
      RoleRepresentation roleRepresentation = new RoleRepresentation();
      roleRepresentation.setName(role.name());
      roleRepresentation.setDescription(role.getDescription());

      if (clientRolesResource.get(role.name()).toRepresentation() == null) {
        clientRolesResource.create(roleRepresentation);
      }
    }
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
    credential.setTemporary(false);
    credential.setValue(employeeRequest.getPassword());
    user.setCredentials(List.of(credential));
    return user;
  }

  private void removeRolesOnUser(String userId) {

    var userResource = realmResource.users().get(userId);

    var realmRoles = userResource.roles().realmLevel().listAll();
    var clientRoles = userResource.roles().clientLevel(getClientIdOnDb()).listAll();

    if (!realmRoles.isEmpty()) {
      userResource.roles().realmLevel().remove(realmRoles);
    }
    if (!clientRoles.isEmpty()) {
      userResource.roles().clientLevel(getClientIdOnDb()).remove(clientRoles);
    }
  }

  private void removeAllRolesOnGroup(String groupId) {
    var groupResource = realmResource.groups().group(groupId);
    var realmRoles = groupResource.roles().realmLevel().listAll();
    var clientRoles = groupResource.roles().clientLevel(getClientIdOnDb()).listAll();
    if (!realmRoles.isEmpty()) {
      groupResource.roles().realmLevel().remove(realmRoles);
    }
    if (!clientRoles.isEmpty()) {
      groupResource.roles().clientLevel(getClientIdOnDb()).remove(clientRoles);
    }
  }

  private List<RoleRepresentation> getClientRoles(List<Role> roles) {
    var clientResource = realmResource.clients().get(getClientIdOnDb());
    return roles.stream()
        .map(
            role -> {
              try {
                return clientResource.roles().get(role.name()).toRepresentation();
              } catch (jakarta.ws.rs.NotFoundException e) {
                throw new NotFoundException("Role " + role + " not found");
              }
            })
        .toList();
  }

  private String getClientIdOnDb() {
    var clientRepresentations = realmResource.clients().findByClientId(idpProperties.getClientId());
    return clientRepresentations.getFirst().getId();
  }
}
