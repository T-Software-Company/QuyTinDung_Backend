package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

// Done
@Entity
@Table(name = "appraisal_report")
public class AppraisalReport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long appraisalReportId;


  @OneToOne(mappedBy = "appraisalReport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private AppraisalPlan appraisalPlan;

  @OneToOne(mappedBy = "appraisalReport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Asset asset;

  @OneToOne(mappedBy = "appraisalReport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Customer customer;

  private Long appraisalPlanId;
  private Long customerId;

  @Enumerated(EnumType.STRING)
  private ApproveStatus approve;
}
