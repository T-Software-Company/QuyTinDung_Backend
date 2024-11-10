package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationMeeting extends AbstractAuditEntity {

  @OneToMany(mappedBy = "valuationMeeting")
  private List<Asset> assets;

  @OneToOne private ValuationMinutes valuationMinutes;
}
