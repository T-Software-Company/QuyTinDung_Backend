package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.LegalDocType;
import com.tsoftware.qtd.constants.EnumType.PassPortType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Getter
@Setter
public class IdentityInfoDTO {
  // CMND CCCD
  @NotBlank String identifyId;

  @NotBlank(message = "FULL_NAME_REQUIRED")
  @Size(min = 3, max = 100, message = "FULL_NAME_SIZE")
  String fullName;

  @NotBlank(message = "ETHNICITY_REQUIRED")
  String ethnicity;

  @NotBlank(message = "RELIGION_REQUIRED")
  String religion;

  @NotNull(message = "GENDER_REQUIRED")
  Gender gender;

  @NotNull(message = "DATE_OF_BIRTH_REQUIRED")
  @Past(message = "DATE_OF_BIRTH_MUST_BE_IN_PAST")
  ZonedDateTime dateOfBirth;

  @NotBlank(message = "NATIONALITY_REQUIRED")
  String nationality;

  @NotBlank(message = "PLACE_OF_BIRTH_REQUIRED")
  String placeOfBirth;

  @NotBlank(message = "PERMANENT_ADDRESS_REQUIRED")
  String permanentAddress;

  @NotNull(message = "ISSUE_DATE_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime issueDate;

  @NotNull(message = "EXPIRATION_DATE_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime expirationDate;

  @NotBlank(message = "ISSUING_AUTHORITY_REQUIRED")
  String issuingAuthority;

  @NotNull String frontPhotoUrl;
  @NotNull String backPhotoUrl;

  @NotNull LegalDocType legalDocType;

  // Passport
  PassPortType passPortType;
}
