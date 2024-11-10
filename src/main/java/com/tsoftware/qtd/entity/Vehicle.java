package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Vehicle extends AbstractAuditEntity {
  private String ownerName;
  private String address;
  private String engineNumber;
  private String chassisNumber;
  private String brand;
  private String modelNumber;
  private String vehicleType;
  private Integer engineCapacity;
  private String color;
  private String loadCapacity;
  private Integer seatCapacity;
  private ZonedDateTime registrationExpiryDate;
  private String licensePlateNumber;
  private ZonedDateTime firstRegistrationDate;
  private ZonedDateTime issueDate;
  private String registrationCertificateNumber;
  private String note;
  private Integer kilometersDriven;
  private String inspectionCertificateNumber;

  @OneToOne private Asset asset;
}
