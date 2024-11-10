package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class CMND extends AbstractAuditEntity {

  private String ethnicity;
  private String religion;
  private ZonedDateTime dateOfBirth;
  private String nationality;
  private String placeOfBirth;
  private String permanentAddress;
  private ZonedDateTime issueDate;
  private ZonedDateTime expirationDate;
  private String issuingAuthority;
  private String frontPhotoURL;
  private String backPhotoURL;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @OneToOne(mappedBy = "cmnd")
  private Customer customer;
}
