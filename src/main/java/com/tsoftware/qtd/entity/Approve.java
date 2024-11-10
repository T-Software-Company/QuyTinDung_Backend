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
public class Approve extends AbstractAuditEntity {

  @ManyToMany private List<Profile> approvers;

  @ManyToOne private AppraisalReport appraisalReport;

  @ManyToOne private ValuationMinutes valuationMinutes;

  @Enumerated(EnumType.STRING)
  private ApproveStatus status;
}
