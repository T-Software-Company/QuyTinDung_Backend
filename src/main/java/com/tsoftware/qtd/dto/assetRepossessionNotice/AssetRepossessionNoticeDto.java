package com.tsoftware.qtd.dto.assetRepossessionNotice;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssetRepossessionNoticeDto {

  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  String message;
  ZonedDateTime repossessionDate;
}
