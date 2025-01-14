package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import com.tsoftware.qtd.entity.LegalDocument;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LegalDocumentMapper;
import com.tsoftware.qtd.repository.LegalDocumentRepository;
import com.tsoftware.qtd.service.LegalDocumentService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LegalDocumentServiceImpl implements LegalDocumentService {

  private final LegalDocumentRepository legaldocumentRepository;

  private final LegalDocumentMapper legaldocumentMapper;

  @Override
  public LegalDocumentResponse create(LegalDocumentResponse legaldocumentResponse) {
    LegalDocument legaldocument = legaldocumentMapper.toEntity(legaldocumentResponse);
    return legaldocumentMapper.toDTO(legaldocumentRepository.save(legaldocument));
  }

  @Override
  public LegalDocumentResponse update(UUID id, LegalDocumentResponse legaldocumentResponse) {
    LegalDocument legaldocument =
        legaldocumentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LegalDocument not found"));
    legaldocumentMapper.updateEntity(legaldocumentResponse, legaldocument);
    return legaldocumentMapper.toDTO(legaldocumentRepository.save(legaldocument));
  }

  @Override
  public void delete(UUID id) {
    legaldocumentRepository.deleteById(id);
  }

  @Override
  public LegalDocumentResponse getById(UUID id) {
    LegalDocument legaldocument =
        legaldocumentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LegalDocument not found"));
    return legaldocumentMapper.toDTO(legaldocument);
  }

  @Override
  public List<LegalDocumentResponse> getAll() {
    return legaldocumentRepository.findAll().stream()
        .map(legaldocumentMapper::toDTO)
        .collect(Collectors.toList());
  }
}
