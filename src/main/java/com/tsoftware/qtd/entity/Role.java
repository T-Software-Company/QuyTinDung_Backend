package com.tsoftware.qtd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
public class Role extends AbstractAuditEntity {

  @Column(unique = true)
  private String name;

  private String description;

  @ManyToMany private List<Group> groups;

  @ManyToMany private List<Employee> employees;
}
