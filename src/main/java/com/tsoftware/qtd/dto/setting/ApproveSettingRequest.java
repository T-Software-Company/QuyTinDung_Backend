package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.validation.IsEnum;
import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApproveSettingRequest {
  private UUID id;
  @NotNull @NotBlank private String name;

  @IsEnum(enumClass = TransactionType.class)
  private String transactionType;

  @NotNull private List<@Valid RoleApproveSettingRequest> roleApproveSettings;
  @NotNull private List<@Valid GroupApproveSettingRequest> groupApproveSettings;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class GroupApproveSettingRequest {
    @NotNull @IsUUID private UUID groupId;
    @NotNull private Integer requiredPercentage;
  }

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  public static class RoleApproveSettingRequest {
    @NotNull
    @IsEnum(enumClass = Role.class)
    private String role;

    @NotNull private Integer requiredCount;
  }
}
