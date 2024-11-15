package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.service.impl.GoogleCloudStorageService;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("/upload/file")
public class UploadController {
  @Autowired private GoogleCloudStorageService googleCloudStorageService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse<String>> upload(
      @RequestPart("file") MultipartFile file, @RequestParam String fileName) throws Exception {
    var url =
        googleCloudStorageService.uploadFile(
            fileName
                + UUID.randomUUID()
                + "."
                + getFileExtension(Objects.requireNonNull(file.getOriginalFilename())),
            file.getInputStream());
    return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "uploaded", url));
  }

  private String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
      return "";
    }
    return fileName.substring(lastIndexOfDot + 1);
  }
}
