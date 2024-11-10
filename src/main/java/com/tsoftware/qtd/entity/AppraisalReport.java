package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import jakarta.persistence.*;
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
public class AppraisalReport extends AbstractAuditEntity {

  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private ApproveStatus approveStatus;

  @ManyToOne private Customer customer;

  @OneToOne(mappedBy = "appraisalReport")
  private AppraisalPlan appraisalPlan;

  @OneToOne(mappedBy = "appraisalReport")
  private ValuationMinutes valuationMinutes;

  @OneToMany(mappedBy = "appraisalReport")
  private List<Asset> asset;

  @OneToMany(mappedBy = "appraisalReport")
  private List<Approve> approves;

  @OneToOne private CreditRating creditRating;
}
