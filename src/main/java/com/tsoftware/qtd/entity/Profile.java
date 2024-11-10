package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import jakarta.persistence.*;
import java.time.LocalDate;
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
public class Profile extends AbstractAuditEntity {

  private String userId;
  private String username;
  private String employeeCode;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate dayOfBirth;
  private String phone;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private Banned banned;

  @Enumerated(EnumType.STRING)
  private EmploymentStatus status;

  @OneToOne private Address address;

  @ManyToMany private List<AppraisalPlan> appraisalPlans;

  @ManyToMany private List<Approve> approves;

  @ManyToMany private ValuationMeeting valuationMeeting;

  @ManyToMany private Group group;
}
