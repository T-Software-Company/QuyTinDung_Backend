package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerDto {

  @NotNull(message = "CUSTOMER_ID_REQUIRED")
  Long customerId;

  @NotBlank(message = "FULL_NAME_REQUIRED")
  @Size(min = 3, max = 100, message = "FULL_NAME_SIZE")
  String fullName;

  @NotBlank(message = "EMAIL_REQUIRED")
  @Email(message = "INVALID_EMAIL_FORMAT")
  String email;

  @NotNull(message = "PHONE_REQUIRED")
  @Pattern(regexp = "^[0-9]{10,15}$", message = "INVALID_PHONE_FORMAT")
  Integer phone;

  @Size(max = 500, message = "NOTE_TOO_LONG")
  String note;

  String signaturePhoto;

  @NotNull(message = "GENDER_REQUIRED")
  Gender gender;

  @NotBlank(message = "STATUS_REQUIRED")
  @Size(min = 3, max = 20, message = "STATUS_SIZE")
  String status;
  
}
