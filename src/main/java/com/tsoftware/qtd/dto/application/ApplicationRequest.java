package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.validation.IsUUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class ApplicationRequest {
  @IsUUID private String id;
}
