package com.tsoftware.qtd.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class AppraisalPlan extends AbstractAuditEntity {
	private String address;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDateAppraisal;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDateAppraisal;
	
	@ManyToMany(mappedBy = "appraisalPlan")
	private List<Profile> participants;
	
	@OneToMany(mappedBy = "appraisalPlan")
	private List<IncomeProof> incomeProof;
	
	@OneToOne
	private AppraisalReport appraisalReport;
	
	@OneToOne
	private Loan loan;
	
	@ManyToOne
	private Customer customer;
	
	@OneToMany(mappedBy = "appraisalPlan")
	private List<PurposeLoanRelated> purposeLoanRelated;
	
	@OneToOne
	private ValuationMinutes valuationMinutes;
	
}
