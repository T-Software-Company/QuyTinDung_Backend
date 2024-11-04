package com.tsoftware.qtd.repository;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tsoftware.qtd.dto.identity.Credential;
import com.tsoftware.qtd.dto.identity.RoleRepresentation;
import com.tsoftware.qtd.dto.identity.TokenExchangeParam;
import com.tsoftware.qtd.dto.identity.TokenExchangeResponse;
import com.tsoftware.qtd.dto.identity.UserCreationParam;
import com.tsoftware.qtd.dto.identity.UserUpdateParam;

@FeignClient(name = "identity-client", url = "${idp.url}") // Định nghĩa Feign Client với tên "identity-client" và URL cơ sở lấy từ biến cấu hình idp.url
public interface IdentityClient {

    // Gọi API để lấy access token từ IDP sử dụng phương thức client_credentials
    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/token", // Đường dẫn đến endpoint lấy token
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // Dữ liệu truyền vào yêu cầu dưới dạng application/x-www-form-urlencoded
    TokenExchangeResponse exchangeToken(@PathVariable("realm") String realm, @RequestBody TokenExchangeParam param);
    // Tham số: realm (realm của IDP) và param (thông tin client ID, client secret và grant type)
    // Trả về: TokenExchangeResponse chứa access token và các thông tin liên quan

    // Tạo người dùng mới trong IDP
    @PostMapping(value = "/admin/realms/{realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE) // Endpoint để tạo người dùng trong realm nhất định
    ResponseEntity<?> createUser(
            @RequestHeader("authorization") String token, // Access token dùng để xác thực
            @PathVariable("realm") String realm, // Realm của IDP
            @RequestBody UserCreationParam param); // UserCreationParam chứa thông tin người dùng cần tạo

    // Cập nhật thông tin người dùng hiện có trong IDP
    @PutMapping(value = "/admin/realms/{realm}/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateUser(
            @RequestHeader("authorization") String token, // Access token cho phép truy cập endpoint
            @PathVariable("realm") String realm, // Realm của IDP
            @PathVariable("userId") String userId, // ID của người dùng cần cập nhật
            @RequestBody UserUpdateParam param); // Thông tin cập nhật của người dùng

    // Lấy tất cả các vai trò (roles) có trong realm
    @GetMapping(value = "/admin/realms/{realm}/roles")
    List<RoleRepresentation> getAllRoles(
            @RequestHeader("authorization") String token, // Access token để xác thực
            @PathVariable("realm") String realm); // Realm của IDP

    // Gán các vai trò cho một người dùng trong realm
    @PostMapping(
            value = "/admin/realms/{realm}/users/{userId}/role-mappings/realm", // Endpoint để gán role cho người dùng
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void assignUserRoles(
            @RequestHeader("authorization") String token, // Access token để xác thực
            @PathVariable("realm") String realm, // Realm của IDP
            @PathVariable("userId") String userId, // ID của người dùng
            @RequestBody List<RoleRepresentation> roles); // Danh sách vai trò để gán cho người dùng

    // Đặt lại mật khẩu cho người dùng
    @PutMapping(
            value = "/admin/realms/{realm}/users/{userId}/reset-password", // Endpoint để đặt lại mật khẩu người dùng
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void updatePassword(
            @RequestHeader("authorization") String token, // Access token để xác thực
            @PathVariable("realm") String realm, // Realm của IDP
            @PathVariable("userId") String userId, // ID của người dùng
            @RequestBody Credential credential); // Credential chứa thông tin mật khẩu mới

    // Lấy các vai trò hiện tại của một người dùng trong realm
    @GetMapping(value = "/admin/realms/{realm}/users/{userId}/role-mappings/realm")
    List<RoleRepresentation> getUserRoles(
            @RequestHeader("authorization") String token, // Access token để xác thực
            @PathVariable("realm") String realm, // Realm của IDP
            @PathVariable("userId") String userId); // ID của người dùng

    // Cập nhật trạng thái kích hoạt của người dùng
    @PutMapping("/admin/realms/{realm}/users/{userId}") // Endpoint để cập nhật trạng thái người dùng
    void updateUserStatus(
            @RequestHeader("Authorization") String token, // Access token để xác thực
            @PathVariable String realm, // Realm của IDP
            @PathVariable String userId, // ID của người dùng
            @RequestBody Map<String, Object> userUpdate); // Map chứa các cập nhật trạng thái cho người dùng, ví dụ "enabled": true/false


}
