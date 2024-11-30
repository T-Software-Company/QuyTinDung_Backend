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

  @Column(nullable = false)
  private String streetAddress;

  @Column(nullable = false)
  private String wardOrCommune;

  @Column(nullable = false)
  private String district;

  @Column(nullable = false)
  private String cityProvince;

  @Column(nullable = false)
  private String country;

  private String detail;
}
