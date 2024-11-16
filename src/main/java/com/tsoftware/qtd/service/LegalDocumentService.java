package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import java.util.List;

public interface LegalDocumentService {
  LegalDocumentResponse create(LegalDocumentResponse legaldocumentResponse);

  LegalDocumentResponse update(Long id, LegalDocumentResponse legaldocumentResponse);

  void delete(Long id);

  LegalDocumentResponse getById(Long id);

  List<LegalDocumentResponse> getAll();
}
