package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherAssetRequest {
  @NotNull private Map<String, Object> metadata;
}
