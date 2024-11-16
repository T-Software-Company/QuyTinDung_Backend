package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class MarketStalls extends AbstractAuditEntity {

  private String stallName;
  private String ownerName;
  private String category;
  private String areaSize;
  private String rentPrice;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime rentStartDate;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime rentEndDate;

  private String location;
  private String contactNumber;
  private boolean isOccupied;
  private String note;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToOne(fetch = FetchType.LAZY)
  private Asset asset;
}
