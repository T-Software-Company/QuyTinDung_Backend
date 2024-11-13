package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanRequest extends AbstractAuditEntity {

  private String purpose;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private BigDecimal amount;
  private String note;

  @Enumerated(EnumType.STRING)
  private BorrowerType borrowerType;

  @Enumerated(EnumType.STRING)
  private LoanSecurityType loanSecurityType;

  @ManyToOne private Credit credit;

  @ManyToOne private Customer customer;

  @Enumerated(EnumType.STRING)
  private AssetType loanCollateralType;
}
