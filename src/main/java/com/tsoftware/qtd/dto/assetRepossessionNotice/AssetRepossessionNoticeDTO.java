package com.tsoftware.qtd.dto.assetRepossessionNotice;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetRepossessionNoticeDTO {

  UUID id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  String message;
  ZonedDateTime repossessionDate;
}
