package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.LegalDocumentDto;
import java.util.List;

public interface LegalDocumentService {
  LegalDocumentDto create(LegalDocumentDto legaldocumentDto);

  LegalDocumentDto update(Long id, LegalDocumentDto legaldocumentDto);

  void delete(Long id);

  LegalDocumentDto getById(Long id);

  List<LegalDocumentDto> getAll();
}
