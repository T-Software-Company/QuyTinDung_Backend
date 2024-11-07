package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

// Done
@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long addressId;

  private String streetAddress;
  private String wardOfCommune;
  private String district;
  private String cityProvince;
  private String country;

  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Customer customer;
}
