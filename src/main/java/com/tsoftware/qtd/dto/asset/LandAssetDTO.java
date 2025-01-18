package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class LandAssetDTO {

  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;
  private TypeOfUse typeOfUse;
}
