package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApprovalRequest {
  @NotNull
  @IsEnum(enumClass = ApprovalStatus.class)
  private String status;

  @NotNull private String comment;
}
