package com.tsoftware.qtd.service.Impl;

import com.tsoftware.qtd.configuration.IdpProperties;
import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.dto.identity.*;
import com.tsoftware.qtd.dto.request.ProfileAdminUpdateRequest;
import com.tsoftware.qtd.dto.request.ProfileUpdateClientRequest;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.dto.response.ProfileResponse;
import com.tsoftware.qtd.dto.response.RoleResponse;
import com.tsoftware.qtd.entity.Profile;
import com.tsoftware.qtd.exception.AppException;
import com.tsoftware.qtd.exception.ErrorCode;
import com.tsoftware.qtd.exception.ErrorNormalizer;
import com.tsoftware.qtd.mapper.ProfileMapper;
import com.tsoftware.qtd.repository.IdentityClient;
import com.tsoftware.qtd.repository.ProfileRepository;
import com.tsoftware.qtd.service.ProfileService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service // Đánh dấu lớp này là một service của Spring
@Slf4j // Bổ sung khả năng ghi log
@RequiredArgsConstructor // Tự động tạo constructor cho các trường final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // Đặt các trường là private và final
public class ProfileServiceImpl implements ProfileService {

  ProfileRepository profileRepository; // Repository xử lý lưu trữ hồ sơ
  ProfileMapper profileMapper; // Mapper chuyển đổi giữa entity và DTO
  IdentityClient identityClient; // Client để gọi API tới hệ thống Identity Provider
  ErrorNormalizer errorNormalizer; // Bộ xử lý lỗi để chuẩn hóa các lỗi trả về từ Identity Provider
  IdpProperties idpProperties; // Cấu hình thông tin của Identity Provider (IDP)

  // Lấy token để xác thực yêu cầu tới Identity Provider
  private String getToken() {
    return identityClient
        .exchangeToken(
            idpProperties.getRealm(),
            new TokenExchangeParam(
                "client_credentials", // Dùng phương thức client_credentials để lấy token
                idpProperties.getClientId(),
                idpProperties.getClientSecret(),
                "openid"))
        .getAccessToken(); // Trả về access token
  }

  // Trích xuất userId từ phản hồi HTTP
  private String extractUserId(ResponseEntity<?> response) {
    String locationHeader = response.getHeaders().get("Location").get(0);
    String[] locationParts = locationHeader.split("/");
    return locationParts[locationParts.length - 1]; // Trả về userId từ URL cuối cùng của Location
  }

  // Lấy danh sách tất cả hồ sơ với vai trò ADMIN
  @Override
  @PreAuthorize("hasRole('ADMIN')") // Chỉ cho phép ADMIN truy cập
  public List<ProfileResponse> getAllProfiles() {
    String token = getToken();
    List<RoleRepresentation> allRoles =
        identityClient.getAllRoles("Bearer " + token, idpProperties.getRealm());

    return profileRepository.findAll().stream()
        .map(profile -> mapProfileWithRoles(profile, allRoles, token))
        .collect(
            Collectors
                .toList()); // Chuyển đổi danh sách profile sang profile response và kèm theo vai
    // trò
  }

  // Map một profile với các vai trò của nó
  private ProfileResponse mapProfileWithRoles(
      Profile profile, List<RoleRepresentation> allRoles, String token) {
    List<String> userRoles;
    ProfileResponse response = profileMapper.toProfileResponse(profile); // Chuyển entity sang DTO

    try {
      userRoles =
          identityClient
              .getUserRoles("Bearer " + token, idpProperties.getRealm(), profile.getUserId())
              .stream()
              .map(RoleRepresentation::getName)
              .toList();

      List<RoleResponse> roles =
          allRoles.stream()
              .filter(
                  role -> userRoles.contains(role.getName())) // Chỉ lấy những vai trò người dùng có
              .map(role -> new RoleResponse(role.getId(), role.getName(), role.getDescription()))
              .collect(Collectors.toList());

      response.setRoles(roles); // Gắn vai trò vào response
    } catch (FeignException e) {
      log.error("Failed to fetch roles for user {}: {}", profile.getUserId(), e.getMessage());
      response.setRoles(List.of()); // Trong trường hợp lỗi, vai trò sẽ để trống
    }
    return response;
  }

  // Lấy hồ sơ của người dùng hiện tại
  @Override
  @PreAuthorize("isAuthenticated()") // Đảm bảo người dùng đã xác thực
  public ProfileResponse getMyProfile() {
    String userId =
        SecurityContextHolder.getContext().getAuthentication().getName(); // Lấy userId từ token
    var profile = findProfileByUserId(userId);
    return mapProfileWithRoles(
        profile,
        identityClient.getAllRoles("Bearer " + getToken(), idpProperties.getRealm()),
        getToken());
  }

  // Đăng ký một hồ sơ mới (ADMIN)
  @Override
  @PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN mới có quyền
  public ProfileResponse registerProfile(RegistrationRequest request) {
    try {
      String token = getToken();
      String userId =
          extractUserId(
              identityClient.createUser(
                  "Bearer " + token, idpProperties.getRealm(), buildUserCreationParam(request)));

      Profile profile = profileMapper.toProfile(request); // Map từ DTO sang entity
      profile.setUserId(userId); // Gán userId lấy từ IDP
      profileRepository.save(profile);

      assignRoleToUserInKeycloak(userId, request.getRoleId(), request.getRoleName());

      if (profile.getBanned() == Banned.LOCKED) {
        deactivateUser(userId);
      }

      return mapProfileWithRoles(
          profile, identityClient.getAllRoles("Bearer " + token, idpProperties.getRealm()), token);
    } catch (FeignException e) {
      throw handleFeignException(e, "Failed to create user"); // Xử lý lỗi khi tạo user
    }
  }

  // Xây dựng tham số tạo user từ RegistrationRequest
  private UserCreationParam buildUserCreationParam(RegistrationRequest request) {
    return UserCreationParam.builder()
        .username(request.getUsername())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .enabled(true)
        .emailVerified(false)
        .credentials(List.of(new Credential("password", request.getPassword(), false)))
        .build(); // Tạo user với mật khẩu không cần đổi
  }

  // Cập nhật thông tin cá nhân cơ bản của người dùng hiện tại
  @Override
  @PreAuthorize("isAuthenticated()")
  public void updateProfile(ProfileUpdateClientRequest request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    updateBasicPersonalInfo(userId, request.getFirstName(), request.getLastName());
  }

  // Cập nhật tên và họ của người dùng, giữ lại các thông tin khác
  private void updateBasicPersonalInfo(String userId, String firstName, String lastName) {
    var profile = findProfileByUserId(userId);

    if (firstName != null) profile.setFirstName(firstName);
    if (lastName != null) profile.setLastName(lastName);

    profileRepository.save(profile);

    if (firstName != null || lastName != null) {
      var updateParam = UserUpdateParam.builder().firstName(firstName).lastName(lastName).build();
      identityClient.updateUser(
          "Bearer " + getToken(), idpProperties.getRealm(), userId, updateParam);
    }
  }

  // Cập nhật hồ sơ bất kỳ user nào bởi admin
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void updateProfileByAdmin(String userId, ProfileAdminUpdateRequest request) {
    var profile = findProfileByUserId(userId);

    handleBannedStatusUpdate(userId, profile.getBanned(), request.getBanned());
    profileMapper.updateProfileFromRequest(request, profile); // Map từ DTO sang entity
    profileRepository.save(profile);

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

  // Tìm hồ sơ theo userId
  private Profile findProfileByUserId(String userId) {
    return profileRepository
        .findByUserId(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
  }

  // Xử lý cập nhật trạng thái khóa của người dùng
  private void handleBannedStatusUpdate(String userId, Banned currentStatus, Banned newStatus) {
    if (newStatus != null && newStatus != currentStatus) {
      if (newStatus == Banned.LOCKED) {
        deactivateUser(userId);
      } else if (newStatus == Banned.ACTIVE) {
        activateUser(userId);
      }
    }
  }

  // Cập nhật tên trong Keycloak
  private void updateUserInKeycloak(String userId, ProfileAdminUpdateRequest request) {
    UserUpdateParam updateParam =
        UserUpdateParam.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .build();
    identityClient.updateUser(
        "Bearer " + getToken(), idpProperties.getRealm(), userId, updateParam);
  }

  // Cập nhật mật khẩu trong Keycloak
  private void updatePasswordInKeycloak(String userId, String newPassword) {
    Credential credential = new Credential("password", newPassword, false);
    identityClient.updatePassword(
        "Bearer " + getToken(), idpProperties.getRealm(), userId, credential);
  }

  // Gán vai trò cho user trong Keycloak
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

  // Kích hoạt user
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void activateUser(String userId) {
    changeUserStatus(userId, true, Banned.ACTIVE);
  }

  // Vô hiệu hóa user
  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void deactivateUser(String userId) {
    changeUserStatus(userId, false, Banned.LOCKED);
  }

  // Thay đổi trạng thái user (kích hoạt/vô hiệu hóa) trong Keycloak
  private void changeUserStatus(String userId, boolean enabled, Banned bannedStatus) {
    Map<String, Object> userUpdate = new HashMap<>();
    userUpdate.put("enabled", enabled);
    identityClient.updateUserStatus(
        "Bearer " + getToken(), idpProperties.getRealm(), userId, userUpdate);

    var profile = findProfileByUserId(userId);
    profile.setBanned(bannedStatus);
    profileRepository.save(profile);
  }

  // Đặt lại mật khẩu người dùng
  public void resetPassword(String userId, String newPassword) {
    updatePasswordInKeycloak(userId, newPassword);
  }

  // Xử lý lỗi từ FeignException
  private AppException handleFeignException(FeignException e, String errorMessage) {
    log.error("{}: {}", errorMessage, e.getMessage());
    return errorNormalizer.handleKeyCloakException(e);
  }
}
