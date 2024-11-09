package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset")
public class Asset {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long assetId;

  @OneToOne private AppraisalReport appraisalReport;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<AssetType> assetType;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Loan loan;
}
