package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class CCCD extends AbstractAuditEntity {

  private String identifyId;
  private ZonedDateTime dateOfBirth;
  private String nationality;
  private String placeOfBirth;
  private String permanentAddress;
  private ZonedDateTime issueDate;
  private ZonedDateTime expirationDate;
  private String issuingAuthority;
  private String frontPhotoURL;
  private String backPhotoURL;
  private String fullName;

  @Enumerated(EnumType.STRING)
  private Gender gender;
}
