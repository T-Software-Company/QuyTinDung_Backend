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
public class LandAndImprovementRequest {

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

  @NotNull(message = "Floor area cannot be null")
  @Positive(message = "Floor area must be greater than 0")
  private BigDecimal floorArea;

  @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Ancillary floor area must be a valid number")
  private String ancillaryFloorArea;

  @NotBlank(message = "Structure type cannot be blank")
  private String structureType;

  @NotNull(message = "Number of floors cannot be null")
  @Min(value = 1, message = "Number of floors must be at least 1")
  private Integer numberOfFloors;

  @NotNull(message = "Construction year cannot be null")
  @Min(value = 1800, message = "Construction year must be later than 1800")
  @Max(value = 2100, message = "Construction year must be reasonable")
  private Integer constructionYear;

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

  @NotNull(message = "Issue date cannot be null")
  @PastOrPresent(message = "Issue date must be in the past or present")
  private ZonedDateTime issueDate;

  @NotNull @Valid private OwnerInfoRequest ownerInfo;
  @NotNull @Valid private TransferInfoRequest transferInfo;
  private Map<String, Object> metadata;
}
