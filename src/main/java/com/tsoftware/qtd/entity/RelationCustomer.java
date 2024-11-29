package com.tsoftware.qtd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "relation_customer")
public class RelationCustomer extends AbstractAuditEntity {
  private String fullName;
  private ZonedDateTime dateOfBirth;
  private String identityId;
  private String phone;
  private String email;

  @OneToOne(cascade = CascadeType.ALL)
  private Address permanentAddress;

  @OneToOne(cascade = CascadeType.ALL)
  private Address currentAddress;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;
}
