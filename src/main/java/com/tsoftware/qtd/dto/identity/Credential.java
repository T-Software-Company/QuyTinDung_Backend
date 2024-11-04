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
public class Credential {
    String type; // Loại thông tin xác thực (ví dụ: "password")
    String value; // Giá trị của thông tin xác thực (ví dụ: mật khẩu thực tế)
    boolean temporary; // Đánh dấu nếu thông tin xác thực chỉ sử dụng tạm thời (ví dụ: mật khẩu tạm)
}
