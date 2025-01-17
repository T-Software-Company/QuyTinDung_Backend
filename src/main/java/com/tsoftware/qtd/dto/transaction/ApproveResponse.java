package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApproveResponse extends AbstractResponse {
  private UUID id;
  private String comment;
  private ApproveStatus status;
  private Employee approver;

  @Getter
  @Setter
  @Builder
  public static class Employee {
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;
    List<Role> roles;
  }
}
