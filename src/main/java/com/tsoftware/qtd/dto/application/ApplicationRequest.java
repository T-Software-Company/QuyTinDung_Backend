package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
  @IsUUID
  @NotNull(message = "Application ID cannot be null")
  private String id;
}
