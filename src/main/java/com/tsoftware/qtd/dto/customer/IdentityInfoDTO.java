package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.IdentityType;
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
  @NotBlank private String identifyId;

  @NotBlank
  @Size(min = 3, max = 100)
  private String fullName;

  @NotBlank private String ethnicity;

  @NotBlank private String religion;

  @NotNull
  @IsEnum(enumClass = Gender.class)
  private String gender;

  @NotNull @Past private ZonedDateTime dateOfBirth;

  @NotBlank private String nationality;

  @NotBlank private String placeOfBirth;

  @NotBlank private String permanentAddress;

  @NotNull ZonedDateTime issueDate;

  @NotNull ZonedDateTime expirationDate;

  @NotBlank private String issuingAuthority;

  @NotNull private String frontPhotoUrl;

  @NotNull private String backPhotoUrl;

  @NotNull
  @IsEnum(enumClass = IdentityType.class)
  private String legalDocType;

  // Passport
  PassPortType passPortType;
}
