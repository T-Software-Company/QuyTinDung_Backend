package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.listener.CustomAuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  @CreatedDate
  protected ZonedDateTime createdAt;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  @LastModifiedDate
  protected ZonedDateTime updatedAt;

  @LastModifiedBy protected String lastModifiedBy;
  @CreatedBy protected String createdBy;

  @Column(columnDefinition = "boolean default false")
  @Builder.Default
  protected Boolean isDeleted = false;
}
