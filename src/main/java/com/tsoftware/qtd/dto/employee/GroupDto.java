package com.tsoftware.qtd.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupDto {
  @NotNull private String id;
  @NotNull private String kcGroupId;
}
