package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import jakarta.persistence.*;
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
public class LandAndImprovement  extends  AbstractAuditEntity{
	private String plotNumber;
	private String mapNumber;
	private String address;
	private String area;
	private String purpose;
	private ZonedDateTime expirationDate;
	private String originOfUsage;
	private String typeOfHousing;
	private String floorArea;
	private String ancillaryFloorArea;
	private String structureType;
	private Integer numberOfFloors;
	private Integer constructionYear;
	private String typeOfOwnership;
	private ZonedDateTime ownershipTerm;
	private String notes;
	private String sharedFacilities;
	private String certificateNumber;
	private String certificateBookNumber;
	private String issuingAuthority;
	private String issueDate;
	
	@Enumerated(EnumType.STRING)
	private TypeOfUse typeOfUse;
	
	@OneToOne
	private Asset asset;
	
	@OneToOne
	private OwnerInfo ownerInfo;
	
	@OneToOne
	private TransferInfo transferInfo;
}
