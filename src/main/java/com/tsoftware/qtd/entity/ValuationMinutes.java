package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationMinutes  extends AbstractAuditEntity{

	@OneToOne(mappedBy = "valuationMinutes")
	private ValuationMeeting valuationMeeting;
	
	@OneToMany(mappedBy = "valuationMinutes")
	private List<Approve> approves;
	
}
