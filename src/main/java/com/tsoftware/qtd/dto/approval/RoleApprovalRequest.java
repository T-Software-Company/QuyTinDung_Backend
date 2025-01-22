package com.tsoftware.qtd.dto.approval;

import java.util.UUID;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleApprovalRequest {
  private UUID id;
}
