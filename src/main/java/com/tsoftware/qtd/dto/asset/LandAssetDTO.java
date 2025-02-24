package com.tsoftware.qtd.dto.asset;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
public class LandAssetDTO {
  private UUID id;
  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;
  private Map<String, Object> metadata;
  private OwnerInfoDTO ownerInfo;
  private TransferInfoDTO transferInfo;
}
