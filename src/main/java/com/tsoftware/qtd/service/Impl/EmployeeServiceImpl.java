package com.tsoftware.qtd.service.Impl;

import com.tsoftware.qtd.configuration.IdpProperties;
import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.dto.identity.*;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.exception.AppException;
import com.tsoftware.qtd.exception.ErrorCode;
import com.tsoftware.qtd.exception.ErrorNormalizer;
import com.tsoftware.qtd.mapper.EmployeeMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.IdentityClient;
import com.tsoftware.qtd.service.EmployeeService;
import feign.FeignException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {

  EmployeeRepository employeeRepository;
  EmployeeMapper employeeMapper;
  IdentityClient identityClient;
  ErrorNormalizer errorNormalizer;
  IdpProperties idpProperties;

  private String getToken() {
    return identityClient
        .exchangeToken(
            idpProperties.getRealm(),
            new TokenExchangeParam(
                "client_credentials",
                idpProperties.getClientId(),
                idpProperties.getClientSecret(),
                "openid"))
        .getAccessToken();
  }

  private String extractUserId(ResponseEntity<?> response) {
    String locationHeader = response.getHeaders().get("Location").get(0);
    String[] locationParts = locationHeader.split("/");
    return locationParts[locationParts.length - 1];
  }

  @Override
  public List<EmployeeResponse> getAllProfiles() {
    String token = getToken();
    List<RoleRepresentation> allRoles =
        identityClient.getAllRoles("Bearer " + token, idpProperties.getRealm());

    return employeeRepository.findAll().stream()
        .map(profile -> mapProfileWithRoles(profile, allRoles, token))
        .collect(Collectors.toList());
  }

  private EmployeeResponse mapProfileWithRoles(
      Employee employee, List<RoleRepresentation> allRoles, String token) {
    List<String> userRoles;
    EmployeeResponse response = employeeMapper.toProfileResponse(employee);

    try {
      userRoles =
          identityClient
              .getUserRoles("Bearer " + token, idpProperties.getRealm(), employee.getUserId())
              .stream()
              .map(RoleRepresentation::getName)
              .toList();

      List<RoleResponse> roles =
          allRoles.stream()
              .filter(role -> userRoles.contains(role.getName()))
              .map(role -> new RoleResponse(role.getId(), role.getName(), role.getDescription()))
              .collect(Collectors.toList());

      response.setRoles(roles);
    } catch (FeignException e) {
      log.error("Failed to fetch roles for user {}: {}", employee.getUserId(), e.getMessage());
      response.setRoles(List.of());
    }
    return response;
  }

  @Override
  public EmployeeResponse getMyProfile() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("User ID from SecurityContext: {}", userId);

    var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    log.info("User Authorities: {}", authorities);

    var profile = findProfileByUserId(userId);
    return mapProfileWithRoles(
        profile,
        identityClient.getAllRoles("Bearer " + getToken(), idpProperties.getRealm()),
        getToken());
  }

  @Override
  public EmployeeResponse registerProfile(AdminRequest request) {
    try {
      String token = getToken();
      String userId =
          extractUserId(
              identityClient.createUser(
                  "Bearer " + token, idpProperties.getRealm(), buildUserCreationParam(request)));

      Employee employee = employeeMapper.toProfile(request);
      employee.setUserId(userId);
      employeeRepository.save(employee);

      assignRoleToUserInKeycloak(userId, request.getRoleId(), request.getRoleName());

      if (employee.getBanned() == Banned.LOCKED) {
        deactivateUser(userId);
      }

      return mapProfileWithRoles(
          employee, identityClient.getAllRoles("Bearer " + token, idpProperties.getRealm()), token);
    } catch (FeignException e) {
      throw handleFeignException(e, "Failed to create user");
    }
  }

  private UserCreationParam buildUserCreationParam(AdminRequest request) {
    return UserCreationParam.builder()
        .username(request.getUsername())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .enabled(true)
        .emailVerified(false)
        .credentials(List.of(new Credential("password", request.getPassword(), false)))
        .build();
  }

  @Override
  public void updateProfile(EmployeeRequest request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    updateBasicPersonalInfo(userId, request.getFirstName(), request.getLastName());
  }

  private void updateBasicPersonalInfo(String userId, String firstName, String lastName) {
    var profile = findProfileByUserId(userId);

    if (firstName != null) profile.setFirstName(firstName);
    if (lastName != null) profile.setLastName(lastName);

    employeeRepository.save(profile);

    if (firstName != null || lastName != null) {
      var updateParam = UserUpdateParam.builder().firstName(firstName).lastName(lastName).build();
      identityClient.updateUser(
          "Bearer " + getToken(), idpProperties.getRealm(), userId, updateParam);
    }
  }

  @Override
  public void updateProfileByAdmin(String userId, AdminRequest request) {
    var profile = findProfileByUserId(userId);

    handleBannedStatusUpdate(userId, profile.getBanned(), request.getBanned());
    employeeMapper.updateProfileFromRequest(request, profile); // Map từ DTO sang entity
    employeeRepository.save(profile);

    if (request.getFirstName() != null || request.getLastName() != null) {
      updateUserInKeycloak(userId, request);
    }
    if (request.getPassword() != null) {
      updatePasswordInKeycloak(userId, request.getPassword());
    }
    if (request.getRoleId() != null && request.getRoleName() != null) {
      assignRoleToUserInKeycloak(userId, request.getRoleId(), request.getRoleName());
    }
  }

  private Employee findProfileByUserId(String userId) {
    return employeeRepository
        .findByUserId(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
  }

  private void handleBannedStatusUpdate(String userId, Banned currentStatus, Banned newStatus) {
    if (newStatus != null && newStatus != currentStatus) {
      if (newStatus == Banned.LOCKED) {
        deactivateUser(userId);
      } else if (newStatus == Banned.ACTIVE) {
        activateUser(userId);
      }
    }
  }

  private void updateUserInKeycloak(String userId, AdminRequest request) {
    UserUpdateParam updateParam =
        UserUpdateParam.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .build();
    identityClient.updateUser(
        "Bearer " + getToken(), idpProperties.getRealm(), userId, updateParam);
  }

  private void updatePasswordInKeycloak(String userId, String newPassword) {
    Credential credential = new Credential("password", newPassword, false);
    identityClient.updatePassword(
        "Bearer " + getToken(), idpProperties.getRealm(), userId, credential);
  }

  private void assignRoleToUserInKeycloak(String userId, String roleId, String roleName) {
    var role = RoleRepresentation.builder().id(roleId).name(roleName).build();
    try {
      identityClient.assignUserRoles(
          "Bearer " + getToken(), idpProperties.getRealm(), userId, List.of(role));
    } catch (FeignException.NotFound e) {
      log.error(
          "Role with ID {} and name {} not found in Keycloak. Skipping role assignment for user {}.",
          roleId,
          roleName,
          userId);
    } catch (FeignException e) {
      log.error("Failed to assign role to user {}: {}", userId, e.getMessage());
    }
  }

  @Override
  public void activateUser(String userId) {
    changeUserStatus(userId, true, Banned.ACTIVE);
  }

  // Vô hiệu hóa user
  @Override
  public void deactivateUser(String userId) {
    changeUserStatus(userId, false, Banned.LOCKED);
  }

  private void changeUserStatus(String userId, boolean enabled, Banned bannedStatus) {
    Map<String, Object> userUpdate = new HashMap<>();
    userUpdate.put("enabled", enabled);
    identityClient.updateUserStatus(
        "Bearer " + getToken(), idpProperties.getRealm(), userId, userUpdate);

    var profile = findProfileByUserId(userId);
    profile.setBanned(bannedStatus);
    employeeRepository.save(profile);
  }

  public void resetPassword(String userId, String newPassword) {
    updatePasswordInKeycloak(userId, newPassword);
  }

  private AppException handleFeignException(FeignException e, String errorMessage) {
    log.error("{}: {}", errorMessage, e.getMessage());
    return errorNormalizer.handleKeyCloakException(e);
  }
}
