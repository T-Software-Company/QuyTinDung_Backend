package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.LegalDocType;
import com.tsoftware.qtd.constants.EnumType.PassPortType;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class IdentityInfoDTO {
  // CMND CCCD
  @NotBlank String identifyId;

  @NotBlank
  @Size(min = 3, max = 100)
  String fullName;

  @NotBlank String ethnicity;

  @NotBlank String religion;

  @NotNull
  @IsEnum(enumClass = Gender.class)
  String gender;

  @NotNull @Past ZonedDateTime dateOfBirth;

  @NotBlank String nationality;

  @NotBlank String placeOfBirth;

  @NotBlank String permanentAddress;

  @NotNull ZonedDateTime issueDate;

  @NotNull ZonedDateTime expirationDate;

  @NotBlank String issuingAuthority;

  @NotNull String frontPhotoUrl;

  @NotNull String backPhotoUrl;

  @NotNull
  @IsEnum(enumClass = LegalDocType.class)
  String legalDocType;

  // Passport
  PassPortType passPortType;
}
