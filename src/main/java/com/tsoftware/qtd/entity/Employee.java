package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.Role;
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
public class Employee extends AbstractAuditEntity {

  @Column(unique = true)
  private String userId;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String employeeCode;

  @Column(unique = true)
  private String email;

  private String firstName;
  private String lastName;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime dayOfBirth;

  private String phone;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private Banned banned;

  @Enumerated(EnumType.STRING)
  private EmploymentStatus status;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @ManyToMany private List<AppraisalPlan> appraisalPlans;

  @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
  private List<Approve> approves;

  @ManyToMany private List<ValuationMeeting> valuationMeetings;

  @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
  private List<Group> groups;

  @Enumerated(EnumType.STRING)
  private List<Role> roles;
}
