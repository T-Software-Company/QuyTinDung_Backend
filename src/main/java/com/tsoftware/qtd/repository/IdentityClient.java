// package com.tsoftware.qtd.repository;
//
// import com.tsoftware.qtd.dto.identity.Credential;
// import com.tsoftware.qtd.dto.identity.TokenExchangeParam;
// import com.tsoftware.qtd.dto.identity.TokenExchangeResponse;
// import com.tsoftware.qtd.dto.identity.UserCreationParam;
// import com.tsoftware.qtd.dto.identity.UserUpdateParam;
// import java.util.List;
// import java.util.Map;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// @FeignClient(name = "identity-client", url = "${idp.url}")
// public interface IdentityClient {
//
//  @PostMapping(
//      value = "/realms/{realm}/protocol/openid-connect/token",
//      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//  TokenExchangeResponse exchangeToken(
//      @PathVariable("realm") String realm, @RequestBody TokenExchangeParam param);
//
//  @PostMapping(value = "/admin/realms/{realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
//  ResponseEntity<?> createUser(
//      @RequestHeader("authorization") String token,
//      @PathVariable("realm") String realm,
//      @RequestBody UserCreationParam param);
//
//  @PutMapping(
//      value = "/admin/realms/{realm}/users/{userId}",
//      consumes = MediaType.APPLICATION_JSON_VALUE)
//  void updateUser(
//      @RequestHeader("authorization") String token,
//      @PathVariable("realm") String realm,
//      @PathVariable("userId") String userId,
//      @RequestBody UserUpdateParam param);
//
//  @GetMapping(value = "/admin/realms/{realm}/roles")
//  List<RoleRepresentation> getAllRoles(
//      @RequestHeader("authorization") String token, @PathVariable("realm") String realm);
//
//  @PostMapping(
//      value = "/admin/realms/{realm}/users/{userId}/role-mappings/realm",
//      consumes = MediaType.APPLICATION_JSON_VALUE)
//  void assignUserRoles(
//      @RequestHeader("authorization") String token,
//      @PathVariable("realm") String realm,
//      @PathVariable("userId") String userId,
//      @RequestBody List<RoleRepresentation> roles);
//
//  @PutMapping(
//      value = "/admin/realms/{realm}/users/{userId}/reset-password",
//      consumes = MediaType.APPLICATION_JSON_VALUE)
//  void updatePassword(
//      @RequestHeader("authorization") String token,
//      @PathVariable("realm") String realm,
//      @PathVariable("userId") String userId,
//      @RequestBody Credential credential);
//
//  @GetMapping(value = "/admin/realms/{realm}/users/{userId}/role-mappings/realm")
//  List<RoleRepresentation> getUserRoles(
//      @RequestHeader("authorization") String token,
//      @PathVariable("realm") String realm,
//      @PathVariable("userId") String userId);
//
//  @PutMapping("/admin/realms/{realm}/users/{userId}")
//  void updateUserStatus(
//      @RequestHeader("Authorization") String token,
//      @PathVariable String realm,
//      @PathVariable String userId,
//      @RequestBody Map<String, Object> userUpdate);
// }
