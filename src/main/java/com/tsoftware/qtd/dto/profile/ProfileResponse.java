package com.tsoftware.qtd.dto.profile;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressVm;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
  String profileId;
  String userId;
  String email;
  String username;
  String firstName;
  String lastName;
  List<RoleResponse> roles;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  LocalDate dob;

  String phone;
  AddressVm address;
  String employee_code;

  Gender gender;
  EmploymentStatus employmentStatus;
  Banned banned;
}
