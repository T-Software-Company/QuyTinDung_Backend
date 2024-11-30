package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Employee extends AbstractAuditEntity {

  @Column(unique = true, nullable = false)
  private String userId;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true)
  private String code;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private ZonedDateTime dayOfBirth;

  @Column(nullable = false)
  private String phone;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private Boolean enabled;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @ManyToMany private List<AppraisalPlan> appraisalPlans;

  @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
  private List<Approve> approves;

  @ManyToMany private List<ValuationMeeting> valuationMeetings;

  @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
  private List<Group> groups;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;
}
