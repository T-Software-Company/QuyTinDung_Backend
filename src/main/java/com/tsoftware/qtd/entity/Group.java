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
@Table(name = "`group`")
public class Group extends AbstractAuditEntity {

  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String kcGroupId;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Employee> employees;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;
}
