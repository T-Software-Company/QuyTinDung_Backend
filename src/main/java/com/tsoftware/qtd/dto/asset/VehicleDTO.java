package com.tsoftware.qtd.dto.asset;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
public class VehicleDTO {
  private UUID id;
  private String model;
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
  private Map<String, Object> metadata;
}
