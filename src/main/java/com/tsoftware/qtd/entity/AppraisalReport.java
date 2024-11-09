package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class AppraisalReport  extends AbstractAuditEntity{
  private Long appraisalPlanId;
  @ManyToOne
  private Customer customer;
  
  @Enumerated(EnumType.STRING)
  private ApproveStatus approveStatus;


  @OneToOne(mappedBy = "appraisalReport")
  private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "appraisalReport")
  private List<Asset> asset;


  
  @OneToMany(mappedBy = "appraisalReport")
  private List<Approve> approves;
  
  
}
