package com.tsoftware.qtd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

// Đánh dấu đây là lớp cấu hình bảo mật
@Configuration
@EnableWebSecurity // Kích hoạt cấu hình bảo mật cho các endpoint web
@EnableMethodSecurity // Kích hoạt bảo mật cấp phương thức dựa trên anotasi như @PreAuthorize
public class SecurityConfig {

  // Định nghĩa các endpoint công khai không yêu cầu xác thực
  private final String[] PUBLIC_ENDPOINTS = {"/register"};

  // Định nghĩa bean cấu hình SecurityFilterChain để thiết lập cấu hình bảo mật cho HttpSecurity
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    // Cấu hình ủy quyền cho các endpoint
    httpSecurity.authorizeHttpRequests(
        request ->
            request
                .requestMatchers(
                    PUBLIC_ENDPOINTS) // Cho phép truy cập công khai vào các endpoint định nghĩa
                // trong PUBLIC_ENDPOINTS
                .permitAll()
                .anyRequest() // Yêu cầu xác thực cho tất cả các endpoint khác
                .authenticated());

    // Thiết lập OAuth2 Resource Server với xác thực JWT
    httpSecurity.oauth2ResourceServer(
        oauth2 ->
            oauth2
                .jwt(
                    jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(
                            jwtAuthenticationConverter())) // Sử dụng bộ chuyển đổi xác thực JWT
                .authenticationEntryPoint(
                    new JwtAuthenticationEntryPoint())); // Xử lý khi không có xác thực hợp lệ

    // Tắt CSRF (Cross-Site Request Forgery) cho API REST (thường không cần CSRF)
    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    return httpSecurity.build();
  }

  // Định nghĩa bean JwtAuthenticationConverter để chuyển đổi JWT thành đối tượng GrantedAuthority
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    // Thiết lập CustomAuthoritiesConverter để chuyển đổi các vai trò từ JWT sang GrantedAuthority
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomAuthoritiesConverter());

    return jwtAuthenticationConverter;
  }
}
