package com.tsoftware.qtd.dto.asset;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import java.util.Map;
import lombok.*;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Builder
public class OtherAssetDTO {

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> properties;
}
