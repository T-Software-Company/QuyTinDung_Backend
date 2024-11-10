package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationMinutes extends AbstractAuditEntity {

  @OneToOne(mappedBy = "valuationMinutes")
  private ValuationMeeting valuationMeeting;

  @OneToOne(mappedBy = "valuationMinutes")
  private AppraisalReport appraisalReport;

  @OneToOne private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "valuationMinutes")
  private List<Approve> approves;
}
