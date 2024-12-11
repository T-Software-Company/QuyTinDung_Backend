package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.configuration.CustomAuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(CustomAuditingEntityListener.class)
@EnableJpaAuditing
public class AbstractAuditEntity {
  @Id
  @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
  protected UUID id;

  @CreatedDate protected ZonedDateTime createdAt;
  @LastModifiedDate protected ZonedDateTime updatedAt;
  @LastModifiedBy protected String lastModifiedBy;
  @CreatedBy protected String createdBy;
  protected Boolean isDeleted;
}
