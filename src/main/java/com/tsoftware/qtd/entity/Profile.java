package com.tsoftware.qtd.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
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
    private Long profileId;   // ID tự động tăng của hồ sơ

    private String userId;    // ID người dùng từ Keycloak
    private String email;     // Địa chỉ email của người dùng
    private String username;  // Tên đăng nhập của người dùng
    private String firstName; // Tên riêng của người dùng
    private String lastName;  // Họ của người dùng
    private LocalDate dob;    // Ngày sinh của người dùng
}
