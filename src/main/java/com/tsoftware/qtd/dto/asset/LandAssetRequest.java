package com.tsoftware.qtd.dto.asset;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
public class LandAssetRequest {

  @NotBlank(message = "Plot number cannot be blank")
  @Size(max = 100, message = "Plot number cannot exceed 100 characters")
  private String plotNumber;

  @NotBlank(message = "Map number cannot be blank")
  @Size(max = 100, message = "Map number cannot exceed 100 characters")
  private String mapNumber;

  @NotBlank(message = "Address cannot be blank")
  @Size(max = 255, message = "Address cannot exceed 255 characters")
  private String address;

  @NotNull(message = "Area cannot be null")
  @Positive(message = "Area must be greater than 0")
  private BigDecimal area;

  @NotBlank(message = "Purpose cannot be blank")
  @Size(max = 255, message = "Purpose cannot exceed 255 characters")
  private String purpose;

  @NotNull(message = "Expiration date cannot be null")
  @FutureOrPresent(message = "Expiration date must be in the present or future")
  private ZonedDateTime expirationDate;

  @NotBlank(message = "Origin of usage cannot be blank")
  @Size(max = 255, message = "Origin of usage cannot exceed 255 characters")
  private String originOfUsage;

  private Map<String, Object> metadata;

  @Valid private OwnerInfoRequest ownerInfo;

  @Valid private TransferInfoRequest transferInfo;
}
