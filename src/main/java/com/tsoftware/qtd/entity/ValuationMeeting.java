package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationMeeting extends AbstractAuditEntity {

  @OneToMany(mappedBy = "valuationMeeting")
  private List<Asset> assets;

  @ManyToMany(mappedBy = "valuationMeetings")
  private List<Employee> participants;

  @OneToOne private ValuationMinutes valuationMinutes;
}
