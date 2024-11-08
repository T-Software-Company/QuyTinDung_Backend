package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "appraisal_plan")
public class AppraisalPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long appraisalPlanId;

      @OneToOne(mappedBy = "appraisalPlan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
      private Customer customer;

      @OneToMany(mappedBy = "appraisalPlan" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
      private PurposeLoanRelated purposeLoanRelated;

      @OneToOne
      private Loan loan;

      @OneToOne(mappedBy = "appraisalPlan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
      private Employee employee;

      @OneToOne
      private IncomeProof incomeProof;

  @OneToOne private AppraisalReport appraisalReport;

  private String addressAppraisal;
  private String participants;

  @Temporal(TemporalType.DATE)
  private Date startDateAppraisal;

  @Temporal(TemporalType.DATE)
  private Date endDateAppraisal;
}
