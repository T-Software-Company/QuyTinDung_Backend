package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Role;
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

  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Employee> employees;

  @Enumerated(EnumType.STRING)
  private List<Role> roles;
}
