package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long profileId; // ID tự động tăng của hồ sơ

  private String userId; // ID người dùng từ Keycloak
  private String username; // Tên đăng nhập của người dùng
  private String employee_code; // Mã nhân viên của người dùng
  private String email; // Địa chỉ email của người dùng
  private String firstName; // Tên riêng của người dùng
  private String lastName; // Họ của người dùng
  private LocalDate dob; // Ngày sinh của người dùng
  private String phone; // Số điện thoại của người dùng
  private String address; // Địa chỉ của người dùng

  @Enumerated(EnumType.STRING)
  private Gender gender; // Giới tính của người dùng (Nam/Nữ)

  @Enumerated(EnumType.STRING)
  private Banned banned; // Trạng thái khóa (Đã khóa/Chưa khóa)

  @Enumerated(EnumType.STRING)
  private EmploymentStatus employmentStatus; // Trạng thái làm việc (Đang làm việc/Đã xin nghỉ)
}
