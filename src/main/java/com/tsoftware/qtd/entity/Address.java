package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Address extends AbstractAuditEntity {

  private String streetAddress;
  private String wardOrCommune;
  private String district;
  private String cityProvince;
  private String country;

  @OneToOne(mappedBy = "address")
  private Profile profile;
}
