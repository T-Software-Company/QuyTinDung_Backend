package com.tsoftware.qtd.dto.identity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenExchangeResponse {
    String accessToken;       // Token truy cập (access token) của người dùng để thực hiện các yêu cầu
    String expiresIn;         // Thời gian hết hạn của token truy cập
    String refreshExpiresIn;  // Thời gian hết hạn của token làm mới (refresh token)
    String tokenType;         // Loại token, "Bearer"
    String idToken;           // Token định danh của người dùng
    String scope;             // Phạm vi quyền truy cập (scope) được cấp cho token
}
