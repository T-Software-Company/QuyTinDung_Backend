package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.service.FileStorageService;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class FileStorageController {
  private final FileStorageService fileStorageService;

  @GetMapping("/download/{fileName}")
  public ResponseEntity<Resource> downloadFile(
      @PathVariable String fileName,
      @RequestParam(value = "attachment", defaultValue = "false") boolean isAttachment) {
    try {
      InputStream inputStream = fileStorageService.downloadFile(fileName);
      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

      return ResponseEntity.ok().headers(headers).body(new InputStreamResource(inputStream));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
