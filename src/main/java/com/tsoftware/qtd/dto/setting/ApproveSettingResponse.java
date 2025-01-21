package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApproveSettingResponse extends AbstractResponse {
  private String name;
  private TransactionType transactionType;
  private List<RoleApproveSettingResponse> roleApproveSettings;
  private List<GroupApproveSettingResponse> groupApproveSettings;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class GroupApproveSettingResponse {
    private UUID groupId;
    private Integer requiredPercentage;
  }

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class RoleApproveSettingResponse {
    private Role role;
    private Integer requiredCount;
  }
}
