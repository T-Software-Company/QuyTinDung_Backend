package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.entity.AssetType;
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
public class ApartmentDto {

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

  @NotNull(message = "ACCESS_TYPE_REQUIRED")
  AssetType accessType;
}
