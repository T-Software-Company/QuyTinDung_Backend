package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Apartment extends AbstractAuditEntity {
	
	
	private String plotNumber;
	private String mapNumber;
	private String address;
	private String area;
	private String purpose;
	private ZonedDateTime expirationDate;
	private String originOfUsage;
	private String typeOfHousing;
	private String Name;
	private String floorArea;
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
	
	@ManyToOne
	private AssetType accessType;
	
	@OneToOne
	private Asset asset;
	
	@OneToOne
	private OwnerInfo ownerInfo;
	
	@OneToOne
	private TransferInfo transferInfo;
}
