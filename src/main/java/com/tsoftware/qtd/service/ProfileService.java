package com.tsoftware.qtd.service;

import java.util.List;

import com.tsoftware.qtd.dto.response.ProfileResponse;
import com.tsoftware.qtd.exception.AppException;
import com.tsoftware.qtd.exception.ErrorCode;
import com.tsoftware.qtd.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tsoftware.qtd.dto.identity.Credential;
import com.tsoftware.qtd.dto.identity.TokenExchangeParam;
import com.tsoftware.qtd.dto.identity.UserCreationParam;
import com.tsoftware.qtd.dto.request.RegistrationRequest;
import com.tsoftware.qtd.exception.ErrorNormalizer;
import com.tsoftware.qtd.repository.IdentityClient;
import com.tsoftware.qtd.repository.ProfileRepository;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor  // Tự động tạo constructor cho các trường final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)  // Đặt tất cả các trường dữ liệu là private và final
public class ProfileService {
    ProfileRepository profileRepository;  // Repository quản lý thao tác với Profile trong DB
    ProfileMapper profileMapper;  // Mapper chuyển đổi giữa Profile entity và DTO
    IdentityClient identityClient;  // Feign client để tương tác với hệ thống xác thực (Keycloak)
    ErrorNormalizer errorNormalizer;  // Xử lý và chuẩn hóa các lỗi từ Keycloak

    // Lấy client ID từ cấu hình để sử dụng trong yêu cầu đến hệ thống xác thực
    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    // Lấy client secret từ cấu hình để sử dụng trong yêu cầu đến hệ thống xác thực
    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    // Endpoint yêu cầu quyền ADMIN để lấy tất cả hồ sơ người dùng
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfileResponse> getAllProfiles() {
        var profiles = profileRepository.findAll();  // Lấy tất cả hồ sơ từ DB
        return profiles.stream().map(profileMapper::toAProfileResponse).toList();  // Ánh xạ sang DTO
    }

    // Lấy hồ sơ của người dùng hiện tại (dựa trên thông tin xác thực)
    public ProfileResponse getMyProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();  // Lấy userId từ JWT (tên người dùng được xác thực)

        var profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));  // Nếu không tìm thấy thì ném lỗi

        return profileMapper.toAProfileResponse(profile);  // Chuyển đổi và trả về dạng DTO
    }

    // Đăng ký người dùng mới và tạo hồ sơ tương ứng
    public ProfileResponse register(RegistrationRequest request) {
        try {
            // Trao đổi token client từ Keycloak
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());

            log.info("TokenInfo {}", token);

            // Gửi yêu cầu tạo người dùng trên Keycloak
            var creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .temporary(false)
                                    .value(request.getPassword())
                                    .build()))
                            .build());

            // Trích xuất userId từ phản hồi của Keycloak
            String userId = extractUserId(creationResponse);
            log.info("UserId {}", userId);

            // Chuyển đổi RegistrationRequest thành Profile entity
            var profile = profileMapper.toProfile(request);
            profile.setUserId(userId);  // Thiết lập userId trong Profile

            // Lưu Profile vào database
            profile = profileRepository.save(profile);

            // Chuyển đổi Profile sang ProfileResponse và trả về
            return profileMapper.toAProfileResponse(profile);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);  // Xử lý lỗi từ Keycloak
        }
    }

    // Trích xuất userId từ phản hồi của Keycloak
    private String extractUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().get("Location").getFirst();  // Lấy tiêu đề Location
        String[] splitedStr = location.split("/");  // Tách chuỗi location để lấy userId
        return splitedStr[splitedStr.length - 1];
    }
}
