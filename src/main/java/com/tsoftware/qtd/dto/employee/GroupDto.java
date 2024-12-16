package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.validation.IsUUID;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupDto {
  @NotNull @IsUUID private String id;
  @NotNull @IsUUID private String kcGroupId;
}
