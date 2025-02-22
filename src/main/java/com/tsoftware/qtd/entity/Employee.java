package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
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
  private String phone;

  @Column(nullable = false)
  private Boolean enabled;

  private String note;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @OneToOne(cascade = CascadeType.ALL)
  private IdentityInfo identityInfo;

  @ManyToMany(mappedBy = "participants")
  private List<AppraisalPlan> appraisalPlans;

  @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
  private List<Approval> approvals;

  @ManyToMany(mappedBy = "participants")
  private List<ValuationMeeting> valuationMeetings;

  @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
  private List<Group> groups;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Application> applicationsAssigned;

  @OneToMany(mappedBy = "employee")
  private List<EmployeeNotification> employeeNotifications;
}
