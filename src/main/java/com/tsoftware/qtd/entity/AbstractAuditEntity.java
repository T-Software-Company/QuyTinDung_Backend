package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.configuration.CustomAuditingEntityListener;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(CustomAuditingEntityListener.class)
public class AbstractAuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate private ZonedDateTime createdAt;
  @LastModifiedDate private ZonedDateTime updatedAt;
  @LastModifiedBy private String lastModifiedBy;
  @CreatedBy private String createdBy;
}
