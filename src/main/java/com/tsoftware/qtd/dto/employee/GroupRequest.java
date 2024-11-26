package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
public class GroupRequest {
  @NotBlank @NotNull private String name;

  @IsEnum(enumClass = Role.class)
  private List<String> roles;
}
