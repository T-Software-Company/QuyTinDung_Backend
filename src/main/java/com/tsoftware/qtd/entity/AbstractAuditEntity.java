package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.configuration.CustomAuditingEntityListener;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(CustomAuditingEntityListener.class)
public class AbstractAuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate protected ZonedDateTime createdAt;
  @LastModifiedDate protected ZonedDateTime updatedAt;
  @LastModifiedBy protected String lastModifiedBy;
  @CreatedBy protected String createdBy;
}
