package com.tsoftware.qtd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
@SuperBuilder
public class Customer extends AbstractAuditEntity {

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

  private String signaturePhoto;
  private String note;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @OneToOne(cascade = CascadeType.ALL)
  private IdentityInfo identityInfo;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
  private List<Application> applications;
}
