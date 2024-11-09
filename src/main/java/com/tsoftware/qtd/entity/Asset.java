package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Asset {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assetId;
	
	@ManyToOne
	private AppraisalReport appraisalReport;
	
	@ManyToOne
	private AssetType assetType;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Loan loan;
	
	@OneToOne(mappedBy = "asset")
	private Apartment apartment;
	
	@ManyToOne
	private AppraisalPlan appraisalPlan;
 
	@ManyToOne
	private AssetRepossessionNotice assetRepossessionNotice;
	
	@ManyToOne
	private  Customer customer;
	
	@ManyToOne private ValuationMeeting valuationMeeting;
}
