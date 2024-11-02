//  Cho phép tất cả các nguồn từ bất kỳ địa chỉ nào truy cập vào ứng dụng

package com.tsoftware.qtd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguration {

    @Bean  // Đánh dấu phương thức này để Spring quản lý và tạo một CorsFilter bean cho toàn bộ ứng dụng
    public CorsFilter corsFilter() {
        // Tạo một cấu hình CORS (Cross-Origin Resource Sharing)
        org.springframework.web.cors.CorsConfiguration corsConfiguration =
                new org.springframework.web.cors.CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*"); // Cho phép tất cả các nguồn (origin) truy cập
        corsConfiguration.addAllowedMethod("*"); // Cho phép tất cả các phương thức HTTP (GET, POST, PUT, DELETE, v.v.)
        corsConfiguration.addAllowedHeader("*"); // Cho phép tất cả các header trong yêu cầu

        // Tạo nguồn cấu hình CORS dựa trên URL
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        // Đăng ký cấu hình CORS cho tất cả các endpoint (/** biểu thị mọi đường dẫn trong ứng dụng)
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        // Trả về một đối tượng CorsFilter với cấu hình đã thiết lập
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
