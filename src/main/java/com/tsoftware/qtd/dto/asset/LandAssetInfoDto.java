package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandAssetInfoDto {

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
}
