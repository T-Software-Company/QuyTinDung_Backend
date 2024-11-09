package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class LandAsset extends AbstractAuditEntity {
  
  
  
  @ManyToOne private Asset asset;
}
