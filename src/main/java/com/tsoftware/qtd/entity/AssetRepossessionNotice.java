package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

  @OneToMany(mappedBy = "assetRepossessionNotice")
  private List<Asset> assets;

  @ManyToOne private Customer customer;

  @ManyToOne private Credit credit;
}
