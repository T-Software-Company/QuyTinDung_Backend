package com.tsoftware.qtd.dto.asset;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class VehicleRequest {

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
