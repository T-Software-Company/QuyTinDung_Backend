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
public class Machinery extends AbstractAuditEntity {
  private String name;
  private String model;
  private String manufacturer;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime manufactureDate;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime purchaseDate;

  private String purchasePrice;
  private String serialNumber;
  private String location;
  private String status;
  private String note;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;
}
