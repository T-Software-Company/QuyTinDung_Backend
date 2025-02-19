package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApprovalSettingRequest {
  private UUID id;

  @IsEnum(enumClass = ProcessType.class)
  private String processType;

  private List<@Valid RoleApprovalSettingRequest> roleApprovalSettings;
  private List<@Valid GroupApprovalSettingRequest> groupApprovalSettings;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class GroupApprovalSettingRequest {
    @NotNull @IsUUID private String groupId;
    @NotNull private Integer requiredPercentage;
  }

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class RoleApprovalSettingRequest {
    @NotNull
    @IsEnum(enumClass = Role.class)
    private String role;

    @NotNull private Integer requiredCount;
  }
}
