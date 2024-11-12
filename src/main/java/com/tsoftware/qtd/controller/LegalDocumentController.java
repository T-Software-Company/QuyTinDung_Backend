package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.LegalDocumentDto;
import com.tsoftware.qtd.response.ApiResponse;
import com.tsoftware.qtd.service.LegalDocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/legaldocuments")
public class LegalDocumentController {

  @Autowired private LegalDocumentService legaldocumentService;

  @PostMapping
  public ResponseEntity<ApiResponse<LegalDocumentDto>> create(
      @RequestBody LegalDocumentDto legaldocumentDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse<>(1000, "Created", legaldocumentService.create(legaldocumentDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<LegalDocumentDto>> update(
      @PathVariable Long id, @RequestBody LegalDocumentDto legaldocumentDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", legaldocumentService.update(id, legaldocumentDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    legaldocumentService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<LegalDocumentDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", legaldocumentService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<LegalDocumentDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", legaldocumentService.getAll()));
  }
}
