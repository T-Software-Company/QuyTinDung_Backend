package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

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

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime startDate;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime endDate;

  @OneToMany(mappedBy = "valuationMeeting")
  private List<Asset> assets;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;

  @ManyToMany(mappedBy = "valuationMeetings")
  private List<Employee> participants;

  @OneToOne private ValuationReport valuationReport;
}
