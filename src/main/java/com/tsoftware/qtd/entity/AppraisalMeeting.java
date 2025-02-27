package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class AppraisalMeeting extends AbstractAuditEntity {
  private String address;
  private String note;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime startDate;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime endDate;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;

  @ManyToMany private List<Employee> participants;
}
