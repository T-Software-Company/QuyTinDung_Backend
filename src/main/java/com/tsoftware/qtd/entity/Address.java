package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
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
