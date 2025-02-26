package com.tsoftware.qtd.dto.asset;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
public class ApartmentRequest {

  @NotBlank(message = "Plot number cannot be blank")
  private String plotNumber;

  @NotBlank(message = "Map number cannot be blank")
  private String mapNumber;

  @NotBlank(message = "Address cannot be blank")
  private String address;

  @NotNull(message = "Area cannot be null")
  @Positive(message = "Area must be greater than 0")
  private BigDecimal area;

  @NotBlank(message = "Purpose cannot be blank")
  private String purpose;

  @FutureOrPresent(message = "Expiration date must be in the present or future")
  private ZonedDateTime expirationDate;

  @NotBlank(message = "Origin of usage cannot be blank")
  private String originOfUsage;

  @NotBlank(message = "Type of housing cannot be blank")
  private String typeOfHousing;

  @NotBlank(message = "Name cannot be blank")
  private String name;

  @NotNull(message = "Floor area cannot be null")
  @Positive(message = "Floor area must be greater than 0")
  private BigDecimal floorArea;

  @NotBlank(message = "Type of ownership cannot be blank")
  private String typeOfOwnership;

  @FutureOrPresent(message = "Ownership term must be in the present or future")
  private ZonedDateTime ownershipTerm;

  private String notes;

  private String sharedFacilities;

  @NotBlank(message = "Certificate number cannot be blank")
  private String certificateNumber;

  @NotBlank(message = "Certificate book number cannot be blank")
  private String certificateBookNumber;

  @NotBlank(message = "Issuing authority cannot be blank")
  private String issuingAuthority;

  @PastOrPresent(message = "Issue date must be in the past or present")
  private ZonedDateTime issueDate;

  private Map<String, Object> metadata;
  @NotNull @Valid private OwnerInfoRequest ownerInfo;
  @NotNull @Valid private TransferInfoRequest transferInfo;
}
