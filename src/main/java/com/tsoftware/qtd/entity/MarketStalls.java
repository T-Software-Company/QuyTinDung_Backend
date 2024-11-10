package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class MarketStalls extends AbstractAuditEntity {
	
	private String stallName;
	private String ownerName;
	private String category;
	private String areaSize;
	private String rentPrice;
	private ZonedDateTime rentStartDate;
	private ZonedDateTime rentEndDate;
	private String location;
	private String contactNumber;
	private boolean isOccupied;
	private String note;
	
	@OneToOne private Asset asset;

}
