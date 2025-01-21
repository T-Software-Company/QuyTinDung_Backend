package com.tsoftware.qtd.dto.asset;

import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
public class OtherAssetRequest {
  private Map<String, Object> properties;
}
