package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDto;
import java.time.ZonedDateTime;
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
public class EmployeeResponse {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  String userId;
  String email;
  String username;
  String firstName;
  String lastName;
  List<Role> roles;
  ZonedDateTime dayOfBirth;
  String phone;
  AddressDto address;
  String employeeCode;
  Gender gender;
  EmploymentStatus employmentStatus;
  Banned banned;
}
