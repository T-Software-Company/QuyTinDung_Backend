package com.tsoftware.qtd.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoftware.qtd.dto.identity.KeyCloakError;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

// Đánh dấu lớp này là một Spring Bean và cho phép ghi log
@Component
@Slf4j
public class ErrorNormalizer {
    private final ObjectMapper objectMapper; // Đối tượng để xử lý JSON
    private final Map<String, ErrorCode> errorCodeMap; // Bản đồ lỗi

    // Hàm khởi tạo
    public ErrorNormalizer() {
        objectMapper = new ObjectMapper(); // Khởi tạo ObjectMapper để chuyển đổi JSON
        errorCodeMap = new HashMap<>(); // Khởi tạo bản đồ chứa các thông báo lỗi từ Keycloak với mã lỗi nội bộ

        // Gán các thông báo lỗi từ Keycloak vào mã lỗi trong hệ thống
        errorCodeMap.put("User exists with same username", ErrorCode.USER_EXISTED);
        errorCodeMap.put("User exists with same email", ErrorCode.EMAIL_EXISTED);
        errorCodeMap.put("User name is missing", ErrorCode.USERNAME_IS_MISSING);
    }

    // Phương thức xử lý ngoại lệ từ Keycloak (được ném bởi Feign client)
    public AppException handleKeyCloakException(FeignException exception) {
        try {
            log.warn("Cannot complete request", exception); // Ghi log cảnh báo

            // Chuyển đổi nội dung lỗi từ JSON sang đối tượng KeyCloakError
            var response = objectMapper.readValue(exception.contentUTF8(), KeyCloakError.class);

            // Kiểm tra thông báo lỗi từ Keycloak có nằm trong errorCodeMap không
            if (Objects.nonNull(response.getErrorMessage())
                    && Objects.nonNull(errorCodeMap.get(response.getErrorMessage()))) {
                // Nếu có, trả về AppException với mã lỗi tương ứng
                return new AppException(errorCodeMap.get(response.getErrorMessage()));
            }
        } catch (JsonProcessingException e) {
            // Nếu không thể chuyển đổi JSON, ghi lỗi
            log.error("Cannot deserialize content", e);
        }

        // Nếu lỗi không nằm trong danh sách hoặc không thể giải mã JSON, trả về ngoại lệ mặc định
        return new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}
