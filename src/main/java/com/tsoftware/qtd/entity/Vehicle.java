package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

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

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime registrationExpiryDate;

  private String licensePlateNumber;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime firstRegistrationDate;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime issueDate;

  private String registrationCertificateNumber;
  private String note;
  private Integer kilometersDriven;
  private String inspectionCertificateNumber;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToOne(fetch = FetchType.LAZY)
  private Asset asset;
}
