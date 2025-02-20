package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
public class MarketStallsRequest {

  @NotBlank(message = "Stall name cannot be blank")
  private String stallName;

  @NotBlank(message = "Owner name cannot be blank")
  private String ownerName;

  @NotBlank(message = "Category cannot be blank")
  private String category;

  @NotNull(message = "Area size cannot be null")
  @Positive(message = "Area size must be a positive number")
  private BigDecimal areaSize;

  @NotNull(message = "Rent price cannot be null")
  @PositiveOrZero(message = "Rent price must be zero or positive")
  private BigDecimal rentPrice;

  @NotNull(message = "Rent start date cannot be null")
  @PastOrPresent(message = "Rent start date must be in the past or present")
  private ZonedDateTime rentStartDate;

  @NotNull(message = "Rent end date cannot be null")
  @Future(message = "Rent end date must be in the future")
  private ZonedDateTime rentEndDate;

  @NotBlank(message = "Location cannot be blank")
  private String location;

  @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Contact number must be a valid phone number")
  private String contactNumber;

  private boolean isOccupied;

  private String note;

  private Map<String, Object> metadata;
}
