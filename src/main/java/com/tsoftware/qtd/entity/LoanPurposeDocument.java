package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanPurposeDocument extends AbstractAuditEntity {

  private String link;
  private String name;

  @ManyToOne private Credit credit;

  @ManyToOne private AppraisalPlan appraisalPlan;
}
