package com.tsoftware.qtd.service;

import java.io.InputStream;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  String uploadFile(String fileName, InputStream fileInputStream) throws Exception;

  InputStream downloadFile(String fileName) throws Exception;

  void deleteFile(String fileName) throws Exception;

  String upload(MultipartFile file);

  default String getFileName(String fileName) {
    String extension = getFileExtension(fileName);
    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
    baseName = baseName.replaceAll("[^a-zA-Z0-9_-]", "_");
    if (baseName.isEmpty()) {
      baseName = "file";
    }
    String uniqueId = UUID.randomUUID().toString();
    return String.format("%s_%s.%s", baseName, uniqueId, extension);
  }

  default String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
      return "";
    }
    return fileName.substring(lastIndexOfDot + 1);
  }
}
