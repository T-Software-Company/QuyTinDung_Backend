package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class AssetRepossessionNotice extends AbstractAuditEntity {

  private String message;
  private ZonedDateTime repossessionDate;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToMany(mappedBy = "assetRepossessionNotice")
  private List<Asset> assets;

  @ManyToOne private Customer customer;

  @ManyToOne private Application application;
}
