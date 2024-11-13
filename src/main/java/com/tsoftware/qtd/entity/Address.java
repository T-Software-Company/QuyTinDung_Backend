package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Address extends AbstractAuditEntity {

  private String streetAddress;
  private String wardOrCommune;
  private String district;
  private String cityProvince;
  private String country;
  private String detail;

  @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
  private Employee employee;

  @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
  private Customer customer;
}
