package com.tsoftware.qtd.service.Impl;

import com.tsoftware.qtd.configuration.IdpProperties;
import com.tsoftware.qtd.dto.employee.EmployeeCreateRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
import com.tsoftware.qtd.exception.KeycloakException;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.service.KeycloakService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakServiceIml implements KeycloakService {
  private final Keycloak keycloak;
  private final IdpProperties idpProperties;

  @Override
  public String createUser(EmployeeCreateRequest employeeCreateRequest) {
    var realmResource = keycloak.realm(idpProperties.getRealm());
    String userId = null;
    var user = getUserRepresentation(employeeCreateRequest);
    var res = realmResource.users().create(user);
    if (res.getStatus() != 201) {
      throw new KeycloakException(res.getStatus());
    }
    try {
      userId = res.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      var roles = employeeCreateRequest.getRoles();
      var clientRepresentations =
          realmResource.clients().findByClientId(idpProperties.getClientId());
      var clientResource = realmResource.clients().get(clientRepresentations.get(0).getId());
      var clientRoles =
          roles.stream().map(role -> clientResource.roles().get(role).toRepresentation()).toList();
      realmResource
          .users()
          .get(userId)
          .roles()
          .clientLevel(clientRepresentations.get(0).getId())
          .add(clientRoles);
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
    var realmResource = keycloak.realm(idpProperties.getRealm());
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

  // chưa rollback nếu error
  @Override
  public void updateUser(EmployeeCreateRequest request, String userId) {
    var realmResource = keycloak.realm(idpProperties.getRealm());

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

    removeAllRoles(userId);
    var clientResource = realmResource.clients().get(idpProperties.getClientId());

    var clientRoles =
        request.getRoles().stream()
            .map(role -> clientResource.roles().get(role).toRepresentation())
            .toList();
    realmResource
        .users()
        .get(userId)
        .roles()
        .clientLevel(idpProperties.getClientId())
        .add(clientRoles);
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

  private static UserRepresentation getUserRepresentation(
      EmployeeCreateRequest employeeCreateRequest) {
    var user = new UserRepresentation();
    user.setUsername(employeeCreateRequest.getUsername());
    user.setEmail(employeeCreateRequest.getEmail());
    user.setFirstName(employeeCreateRequest.getFirstName());
    user.setLastName(employeeCreateRequest.getLastName());
    user.setEnabled(true);
    user.setEmailVerified(false);
    var credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setTemporary(false);
    credential.setValue(employeeCreateRequest.getPassword());
    user.setCredentials(List.of(credential));
    return user;
  }

  public void removeAllRoles(String userId) {
    var realmResource = keycloak.realm(idpProperties.getRealm());
    var userResource = realmResource.users().get(userId);

    var realmRoles = userResource.roles().realmLevel().listAll();
    var clientRoles = userResource.roles().clientLevel("client-id").listAll();

    if (!realmRoles.isEmpty()) {
      userResource.roles().realmLevel().remove(realmRoles);
    }
    if (!clientRoles.isEmpty()) {
      userResource.roles().clientLevel("client-id").remove(clientRoles);
    }
  }
}
