package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.configuration.CustomAuditingEntityListener;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@EntityListeners(CustomAuditingEntityListener.class)
public class AbstractAuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected UUID id;

  @CreatedDate protected ZonedDateTime createdAt;
  @LastModifiedDate protected ZonedDateTime updatedAt;
  @LastModifiedBy protected String lastModifiedBy;
  @CreatedBy protected String createdBy;

  @Column(columnDefinition = "boolean default false")
  @Builder.Default
  protected Boolean isDeleted = false;
}
