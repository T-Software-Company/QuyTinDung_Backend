package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cccd")
public class CCCD {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cccdID;

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
