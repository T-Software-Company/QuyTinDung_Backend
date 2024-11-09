package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
@SuperBuilder
public class Disbursement extends AbstractAuditEntity{
	
	private Long loanLimit;
	private Long amountReceived;
	private Long currentOutstandingDebt;
	private ZonedDateTime dateOfLoanReceipt;
	private ZonedDateTime loanTerm;
	private Float interestRate;
	private String repaymentSchedule;
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private  Loan loan;

}
