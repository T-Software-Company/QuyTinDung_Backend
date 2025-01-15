package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.LegalDocType;
import com.tsoftware.qtd.constants.EnumType.PassPortType;
import jakarta.persistence.*;
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
@Entity
@Table
public class IdentityInfo extends AbstractAuditEntity {
  String identifyId;
  String fullName;
  String ethnicity;
  String religion;
  Gender gender;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  ZonedDateTime dateOfBirth;

  String nationality;
  String placeOfBirth;
  String permanentAddress;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  ZonedDateTime issueDate;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  ZonedDateTime expirationDate;

  String issuingAuthority;

  @Enumerated(EnumType.ORDINAL)
  LegalDocType legalDocType;

  String frontPhotoUrl;
  String backPhotoUrl;

  // Passport
  @Enumerated(EnumType.ORDINAL)
  PassPortType passPortType;
}
