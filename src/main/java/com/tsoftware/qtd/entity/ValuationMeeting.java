package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
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
  private String address;
  private String note;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime startDate;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime endDate;

  @OneToMany(mappedBy = "valuationMeeting")
  private List<Asset> assets;

  @OneToOne(fetch = FetchType.LAZY)
  private Credit credit;

  @ManyToMany(mappedBy = "valuationMeetings")
  private List<Employee> participants;

  @OneToOne private ValuationReport valuationReport;
}
