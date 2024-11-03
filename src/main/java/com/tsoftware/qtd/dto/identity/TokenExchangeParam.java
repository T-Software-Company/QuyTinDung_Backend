package com.tsoftware.qtd.dto.identity;

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
public class TokenExchangeParam {
  String grant_type; // Loại grant (cấp quyền) yêu cầu, ví dụ: "client_credentials"
  String client_id; // ID của client, cần thiết để xác thực ứng dụng với máy chủ
  String client_secret; // Mật khẩu bí mật của client, kết hợp với client_id để xác thực
  String scope; // Phạm vi quyền truy cập yêu cầu, ví dụ: "openid profile"
}
