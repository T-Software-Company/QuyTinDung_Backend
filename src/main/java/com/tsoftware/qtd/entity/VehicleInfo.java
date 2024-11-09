package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class VehicleInfo extends AbstractAuditEntity {
  @OneToOne private Asset asset;
}
