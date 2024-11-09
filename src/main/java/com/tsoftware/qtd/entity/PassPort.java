package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.PassPortType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class PassPort extends AbstractAuditEntity {
  @OneToOne(mappedBy = "passPort")
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

  @Enumerated(EnumType.STRING)
  private PassPortType passPortType;
}
