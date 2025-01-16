package com.tsoftware.qtd.dto.transaction;

import java.util.UUID;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleApproveRequest {
  private UUID id;
}
