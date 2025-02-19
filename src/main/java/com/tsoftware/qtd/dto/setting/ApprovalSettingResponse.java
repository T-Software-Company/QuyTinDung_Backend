package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.constants.EnumType.Role;
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
public class ApprovalSettingResponse extends AbstractResponse {
  private ProcessType processType;
  private List<RoleApprovalSettingResponse> roleApprovalSettings;
  private List<GroupApprovalSettingResponse> groupApprovalSettings;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class GroupApprovalSettingResponse {
    private UUID groupId;
    private Integer requiredPercentage;
  }

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class RoleApprovalSettingResponse {
    private Role role;
    private Integer requiredCount;
  }
}
