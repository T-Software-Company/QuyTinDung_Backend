package com.tsoftware.qtd.dto.identity;

import java.util.List;
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
public class UserCreationParam {
    String username;            // Tên người dùng
    boolean enabled;            // Trạng thái kích hoạt của tài khoản (true nếu tài khoản đã được kích hoạt)
    String email;               // Địa chỉ email của người dùng
    boolean emailVerified;      // Xác nhận email (true nếu email đã được xác minh)
    String firstName;           // Tên riêng của người dùng
    String lastName;            // Họ của người dùng
    List<Credential> credentials; // Danh sách thông tin xác thực của người dùng (ví dụ: mật khẩu)
}
