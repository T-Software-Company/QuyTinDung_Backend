package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandAssetDto {


  Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;
  private TypeOfUse typeOfUse;
}
