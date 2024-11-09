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
public class Vehicle extends AbstractAuditEntity {
  @OneToOne private Asset asset;
}
