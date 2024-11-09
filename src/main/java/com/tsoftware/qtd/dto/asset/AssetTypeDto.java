package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.entity.LandAsset;
import com.tsoftware.qtd.entity.Vehicle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetTypeDto {

  @NotNull(message = "ID_REQUIRED")
  Long id;

  @NotNull(message = "CREATED_AT_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime createdAt;

  @NotNull(message = "UPDATED_AT_REQUIRED")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  ZonedDateTime updatedAt;

  @NotNull(message = "LAST_MODIFIED_BY_REQUIRED")
  @Size(min = 1, message = "LAST_MODIFIED_BY_INVALID")
  String lastModifiedBy;

  @NotNull(message = "CREATED_BY_REQUIRED")
  @Size(min = 1, message = "CREATED_BY_INVALID")
  String createdBy;

  @Valid
  @NotNull(message = "ASSET_REQUIRED")
  AssetDto asset;

  @Valid LandAsset landAsset;

  @Valid Vehicle vehicle;

  @Valid ApartmentDto apartmentInfo;

  @NotBlank(message = "NAME_ASSET_REQUIRED")
  @Size(min = 1, message = "NAME_ASSET_INVALID")
  String nameAsset;
}
