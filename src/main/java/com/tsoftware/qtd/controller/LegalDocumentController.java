package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import com.tsoftware.qtd.service.LegalDocumentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/legal-documents")
@RequiredArgsConstructor
public class LegalDocumentController {

  private final LegalDocumentService legaldocumentService;

  @PostMapping
  public ResponseEntity<ApiResponse<LegalDocumentResponse>> create(
      @RequestBody LegalDocumentResponse legaldocumentResponse) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(1000, "Created", legaldocumentService.create(legaldocumentResponse)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LegalDocumentResponse>> update(
      @PathVariable UUID id, @RequestBody LegalDocumentResponse legaldocumentResponse) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", legaldocumentService.update(id, legaldocumentResponse)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    legaldocumentService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LegalDocumentResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", legaldocumentService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LegalDocumentResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", legaldocumentService.getAll()));
  }
}
