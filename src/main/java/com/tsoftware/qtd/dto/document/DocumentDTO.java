package com.tsoftware.qtd.dto.document;

import com.tsoftware.qtd.constants.EnumType.DocumentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentDTO {
  private Long id;
  private String url;
  private String title;
  private DocumentType type;
  private Boolean isUsed;
}
