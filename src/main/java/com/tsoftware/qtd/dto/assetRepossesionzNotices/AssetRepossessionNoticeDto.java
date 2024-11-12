package com.tsoftware.qtd.dto.assetRepossesionzNotices;

import java.time.ZonedDateTime;

public class AssetRepossessionNoticeDto {

    Long id;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    String lastModifiedBy;
    String createdBy;
    String message;
    ZonedDateTime repossessionDate;
}
