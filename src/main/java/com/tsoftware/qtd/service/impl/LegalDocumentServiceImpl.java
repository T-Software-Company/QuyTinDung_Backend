package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import com.tsoftware.qtd.entity.LegalDocument;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LegalDocumentMapper;
import com.tsoftware.qtd.repository.LegalDocumentRepository;
import com.tsoftware.qtd.service.LegalDocumentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegalDocumentServiceImpl implements LegalDocumentService {

  @Autowired private LegalDocumentRepository legaldocumentRepository;

  @Autowired private LegalDocumentMapper legaldocumentMapper;

  @Override
  public LegalDocumentResponse create(LegalDocumentResponse legaldocumentResponse) {
    LegalDocument legaldocument = legaldocumentMapper.toEntity(legaldocumentResponse);
    return legaldocumentMapper.toDto(legaldocumentRepository.save(legaldocument));
  }

  @Override
  public LegalDocumentResponse update(Long id, LegalDocumentResponse legaldocumentResponse) {
    LegalDocument legaldocument =
        legaldocumentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LegalDocument not found"));
    legaldocumentMapper.updateEntity(legaldocumentResponse, legaldocument);
    return legaldocumentMapper.toDto(legaldocumentRepository.save(legaldocument));
  }

  @Override
  public void delete(Long id) {
    legaldocumentRepository.deleteById(id);
  }

  @Override
  public LegalDocumentResponse getById(Long id) {
    LegalDocument legaldocument =
        legaldocumentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LegalDocument not found"));
    return legaldocumentMapper.toDto(legaldocument);
  }

  @Override
  public List<LegalDocumentResponse> getAll() {
    return legaldocumentRepository.findAll().stream()
        .map(legaldocumentMapper::toDto)
        .collect(Collectors.toList());
  }
}
