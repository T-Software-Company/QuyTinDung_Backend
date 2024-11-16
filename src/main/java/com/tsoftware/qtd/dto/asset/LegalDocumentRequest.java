package com.tsoftware.qtd.dto.asset;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LegalDocumentRequest {
  private String name;
  private String link;
}
