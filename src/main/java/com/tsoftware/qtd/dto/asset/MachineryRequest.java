package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
public class MachineryRequest {

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 255, message = "Name cannot exceed 255 characters")
  private String name;

  @NotBlank(message = "Model cannot be blank")
  @Size(max = 255, message = "Model cannot exceed 255 characters")
  private String model;

  @NotBlank(message = "Manufacturer cannot be blank")
  @Size(max = 255, message = "Manufacturer cannot exceed 255 characters")
  private String manufacturer;

  @NotNull(message = "Manufacture date cannot be null")
  @PastOrPresent(message = "Manufacture date must be in the past or present")
  private ZonedDateTime manufactureDate;

  @NotNull(message = "Purchase date cannot be null")
  @PastOrPresent(message = "Purchase date must be in the past or present")
  private ZonedDateTime purchaseDate;

  @NotNull(message = "Purchase price cannot be null")
  @Positive(message = "Purchase price must be greater than 0")
  @Digits(integer = 12, fraction = 2, message = "Purchase price must have up to 2 decimal places")
  private BigDecimal purchasePrice;

  @NotBlank(message = "Serial number cannot be blank")
  @Size(max = 255, message = "Serial number cannot exceed 255 characters")
  private String serialNumber;

  @NotBlank(message = "Location cannot be blank")
  @Size(max = 255, message = "Location cannot exceed 255 characters")
  private String location;

  @NotBlank(message = "Status cannot be blank")
  @Size(max = 100, message = "Status cannot exceed 100 characters")
  private String status;

  private String note;

  private Map<String, Object> metadata;
}
