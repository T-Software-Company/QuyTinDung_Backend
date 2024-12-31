package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Apartment extends AbstractAuditEntity {

  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime expirationDate;

  private String originOfUsage;
  private String typeOfHousing;
  private String Name;
  private BigDecimal floorArea;
  private String typeOfOwnership;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime ownershipTerm;

  private String notes;
  private String sharedFacilities;
  private String certificateNumber;
  private String certificateBookNumber;
  private String issuingAuthority;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime issueDate;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private TypeOfUse typeOfUse;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private OwnerInfo ownerInfo;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private TransferInfo transferInfo;
}
