package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import jakarta.persistence.Table;
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
public class CCCD extends AbstractAuditEntity {

  @OneToOne(mappedBy = "cccd")
  private Customer customer;

  private String fullName;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private ZonedDateTime dateOfBirth;
  private String nationality;
  private String placeOfBirth;
  private String permanentAddress;
  private ZonedDateTime issueDate;
  private ZonedDateTime expirationDate;
  private String issuingAuthority;
  private String frontPhotoURL;
  private String backPhotoURL;
}
