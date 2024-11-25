package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class CMND extends AbstractAuditEntity {

  private String identifyId;
  private String ethnicity;
  private String religion;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime dateOfBirth;

  private String nationality;
  private String placeOfBirth;
  private String permanentAddress;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime issueDate;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime expirationDate;

  private String issuingAuthority;
  private String frontPhotoURL;
  private String backPhotoURL;

  @Enumerated(EnumType.STRING)
  private Gender gender;
}
