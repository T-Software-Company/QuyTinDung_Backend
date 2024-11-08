package com.tsoftware.qtd.entity;
import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class IncomeProof extends AbstractAuditEntity{
    @ManyToOne( cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne(mappedBy = "incomeProof", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private AppraisalPlan appraisalPlan;

    private String linkFile;
    @Enumerated(EnumType.STRING)
    private IncomeProofType incomeProofType;


}
