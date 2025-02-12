package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanRequest extends AbstractAuditEntity {

  private String purpose;
  private BigDecimal amount;
  private String note;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private BorrowerType borrowerType;

  @Enumerated(EnumType.ORDINAL)
  private LoanSecurityType loanSecurityType;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;

  @Enumerated(EnumType.ORDINAL)
  private List<AssetType> loanCollateralTypes;
}
