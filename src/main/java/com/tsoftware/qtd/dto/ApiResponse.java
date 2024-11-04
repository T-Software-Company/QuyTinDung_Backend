package com.tsoftware.qtd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    // Mã phản hồi mặc định là 1000
    @Builder.Default
    private int code = 1000;

    // Thông báo phản hồi, có thể là lỗi hoặc thành công
    private String message;

    // Kết quả (dữ liệu) trả về từ API, kiểu T là kiểu tổng quát
    private T result;
}
