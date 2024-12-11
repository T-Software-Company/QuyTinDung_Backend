package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import java.util.List;
import java.util.UUID;

public interface LegalDocumentService {
  LegalDocumentResponse create(LegalDocumentResponse legaldocumentResponse);

  LegalDocumentResponse update(UUID id, LegalDocumentResponse legaldocumentResponse);

  void delete(UUID id);

  LegalDocumentResponse getById(UUID id);

  List<LegalDocumentResponse> getAll();
}
