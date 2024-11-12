package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.LegalDocumentDto;
import com.tsoftware.qtd.entity.LegalDocument;
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
  public LegalDocumentDto create(LegalDocumentDto legaldocumentDto) {
    LegalDocument legaldocument = legaldocumentMapper.toEntity(legaldocumentDto);
    return legaldocumentMapper.toDto(legaldocumentRepository.save(legaldocument));
  }

  @Override
  public LegalDocumentDto update(Long id, LegalDocumentDto legaldocumentDto) {
    LegalDocument legaldocument =
        legaldocumentRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("LegalDocument not found"));
    legaldocumentMapper.updateEntity(legaldocumentDto, legaldocument);
    return legaldocumentMapper.toDto(legaldocumentRepository.save(legaldocument));
  }

  @Override
  public void delete(Long id) {
    legaldocumentRepository.deleteById(id);
  }

  @Override
  public LegalDocumentDto getById(Long id) {
    LegalDocument legaldocument =
        legaldocumentRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("LegalDocument not found"));
    return legaldocumentMapper.toDto(legaldocument);
  }

  @Override
  public List<LegalDocumentDto> getAll() {
    return legaldocumentRepository.findAll().stream()
        .map(legaldocumentMapper::toDto)
        .collect(Collectors.toList());
  }
}
