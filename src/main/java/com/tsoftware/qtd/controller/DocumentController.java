package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.constants.EnumType.DocumentType;
import com.tsoftware.qtd.dto.document.DocumentDTO;
import com.tsoftware.qtd.service.DocumentService;
import com.tsoftware.qtd.service.FileStorageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentService documentService;
  private final FileStorageService fileStorageService;

  @PostMapping("/upload")
  public ResponseEntity<DocumentDTO> upload(
      @RequestParam("file") MultipartFile file,
      @RequestParam(value = "type", required = false) DocumentType type) {
    return ResponseEntity.ok(documentService.upload(file, type));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DocumentDTO> getDocument(@PathVariable UUID id) {
    return ResponseEntity.ok(documentService.getDocument(id));
  }
}
