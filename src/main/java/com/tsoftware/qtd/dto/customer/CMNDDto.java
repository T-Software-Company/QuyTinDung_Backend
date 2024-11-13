package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Getter
@Setter
public class CMNDDto {

  @NotBlank private String identifyId;

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

  @NotBlank(message = "FRONT_PHOTO_URL_REQUIRED")
  @Pattern(regexp = "^(http|https)://.*", message = "INVALID_FRONT_PHOTO_URL")
  String frontPhotoURL;

  @NotBlank(message = "BACK_PHOTO_URL_REQUIRED")
  @Pattern(regexp = "^(http|https)://.*", message = "INVALID_BACK_PHOTO_URL")
  String backPhotoURL;
}
