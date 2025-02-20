package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
public class VehicleRequest {

  @NotBlank(message = "Model cannot be blank")
  private String model;

  @NotBlank(message = "Owner name cannot be blank")
  private String ownerName;

  @NotBlank(message = "Address cannot be blank")
  private String address;

  @NotBlank(message = "Engine number cannot be blank")
  private String engineNumber;

  @NotBlank(message = "Chassis number cannot be blank")
  private String chassisNumber;

  @NotBlank(message = "Brand cannot be blank")
  private String brand;

  @NotBlank(message = "Model number cannot be blank")
  private String modelNumber;

  @NotBlank(message = "Vehicle type cannot be blank")
  private String vehicleType;

  @NotNull(message = "Engine capacity cannot be null")
  @Positive(message = "Engine capacity must be greater than 0")
  private Integer engineCapacity;

  @NotBlank(message = "Color cannot be blank")
  private String color;

  @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Load capacity must be a valid number")
  private String loadCapacity;

  @NotNull(message = "Seat capacity cannot be null")
  @Min(value = 1, message = "Seat capacity must be at least 1")
  private Integer seatCapacity;

  @FutureOrPresent(message = "Registration expiry date must be in the present or future")
  private ZonedDateTime registrationExpiryDate;

  @NotBlank(message = "License plate number cannot be blank")
  private String licensePlateNumber;

  @NotNull(message = "First registration date cannot be null")
  @PastOrPresent(message = "First registration date must be in the past or present")
  private ZonedDateTime firstRegistrationDate;

  @NotNull(message = "Issue date cannot be null")
  @PastOrPresent(message = "Issue date must be in the past or present")
  private ZonedDateTime issueDate;

  @NotBlank(message = "Registration certificate number cannot be blank")
  private String registrationCertificateNumber;

  private String note;

  @NotNull(message = "Kilometers driven cannot be null")
  @Min(value = 0, message = "Kilometers driven must be 0 or more")
  private Integer kilometersDriven;

  @NotBlank(message = "Inspection certificate number cannot be blank")
  private String inspectionCertificateNumber;

  private Map<String, Object> metadata;
}
