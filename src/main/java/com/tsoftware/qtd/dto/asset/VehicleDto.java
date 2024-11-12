package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class VehicleDto {


  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  String ownerName;
  String address;
  String engineNumber;
  String chassisNumber;
  String brand;
  String modelNumber;
  String vehicleType;
  Integer engineCapacity;
  String color;
  String loadCapacity;
  Integer seatCapacity;
  ZonedDateTime registrationExpiryDate;
  String licensePlateNumber;
  ZonedDateTime firstRegistrationDate;
  ZonedDateTime issueDate;
  String registrationCertificateNumber;
  String note;
  Integer kilometersDriven;
  String inspectionCertificateNumber;
}
