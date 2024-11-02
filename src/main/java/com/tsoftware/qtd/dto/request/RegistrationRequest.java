package com.tsoftware.qtd.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
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
public class RegistrationRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    String username; // Tên người dùng, yêu cầu tối thiểu 4 ký tự

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password; // Mật khẩu, yêu cầu tối thiểu 6 ký tự

    String email; // Email người dùng
    String firstName; // Tên
    String lastName; // Họ

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob; // Ngày sinh, định dạng theo chuẩn ISO
}
