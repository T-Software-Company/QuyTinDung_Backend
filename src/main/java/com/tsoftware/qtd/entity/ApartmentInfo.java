package com.tsoftware.qtd.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="apartment_info")
public class ApartmentInfo extends AbstractAuditEntity{

    @OneToOne(mappedBy = "apartmentInfo")
    private AssetType accessType;
}
