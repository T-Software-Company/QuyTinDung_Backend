package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationMeeting extends AbstractAuditEntity{
	
	@OneToMany(mappedBy = "valuationMeeting")
	private List<Asset>	 assets;
	@OneToOne
	private ValuationMinutes valuationMinutes;
}
