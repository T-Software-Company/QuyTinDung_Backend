package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Machinery extends AbstractAuditEntity {
  private String name;
  private String model;
  private String manufacturer;
  private ZonedDateTime manufactureDate;
  private ZonedDateTime purchaseDate;
  private String purchasePrice;
  private String serialNumber;
  private String location;
  private String status;
  private String note;

  @OneToOne private Asset asset;
}
