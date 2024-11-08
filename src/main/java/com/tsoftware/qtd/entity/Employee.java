package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Banned;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Employee extends  AbstractAuditEntity {
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private ValuationMeeting valuationMeeting;
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Disbursement disbursement;
//      @OneToOne(mappedBy = "employee")
//      private Approve approve;

        private Long employeeCode;
        private String employeeEmail;
        private String fistName;
        private String lastName;
        private String address;
        @Enumerated(EnumType.STRING)
        private Banned banned;
        private ZonedDateTime dayOfBirth;
        private Long phone;

}
