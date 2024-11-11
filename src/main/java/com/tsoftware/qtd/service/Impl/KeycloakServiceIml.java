package com.tsoftware.qtd.service.Impl;

import com.tsoftware.qtd.configuration.IdpProperties;
import com.tsoftware.qtd.dto.employee.EmployeeCreateRequest;
import com.tsoftware.qtd.dto.employee.ProfileRequest;
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
  public void createUser(EmployeeCreateRequest employeeCreateRequest) {
    var realmResource = keycloak.realm(idpProperties.getRealm());
    String userId = null;
    var user = getUserRepresentation(employeeCreateRequest);
    try (var res = realmResource.users().create(user)) {

      userId = res.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
      var roles = employeeCreateRequest.getRoles();
      var clientResource = realmResource.clients().get(idpProperties.getClientId());
      var clientRoles =
          roles.stream().map(role -> clientResource.roles().get(role).toRepresentation()).toList();
      realmResource
          .users()
          .get(userId)
          .roles()
          .clientLevel(idpProperties.getClientId())
          .add(clientRoles);
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
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    realmResource.users().get(userId).update(user);
  }

  @Override
  public void updateUser(EmployeeCreateRequest request, String userId) {}

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
}
