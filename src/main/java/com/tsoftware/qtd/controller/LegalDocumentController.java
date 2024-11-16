package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.asset.LegalDocumentResponse;
import com.tsoftware.qtd.service.LegalDocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/legal-documents")
public class LegalDocumentController {

  @Autowired private LegalDocumentService legaldocumentService;

  @PostMapping
  public ResponseEntity<ApiResponse<LegalDocumentResponse>> create(
      @RequestBody LegalDocumentResponse legaldocumentResponse) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ApiResponse<>(1000, "Created", legaldocumentService.create(legaldocumentResponse)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LegalDocumentResponse>> update(
      @PathVariable Long id, @RequestBody LegalDocumentResponse legaldocumentResponse) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", legaldocumentService.update(id, legaldocumentResponse)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    legaldocumentService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LegalDocumentResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", legaldocumentService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LegalDocumentResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", legaldocumentService.getAll()));
  }
}
