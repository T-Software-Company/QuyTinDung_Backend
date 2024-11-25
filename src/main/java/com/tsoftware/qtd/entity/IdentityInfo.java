package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.PassPortType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "identity_info")
public class IdentityInfo extends AbstractAuditEntity {
  String identifyId;
  String fullName;
  String ethnicity;
  String religion;
  Gender gender;
  ZonedDateTime dateOfBirth;
  String nationality;
  String placeOfBirth;
  String permanentAddress;
  ZonedDateTime issueDate;
  ZonedDateTime expirationDate;
  String issuingAuthority;
  String frontPhotoUrl;
  String backPhotoUrl;

  // Passport
  PassPortType passPortType;

  @OneToOne(mappedBy = "identityInfo")
  private Customer customer;
}
