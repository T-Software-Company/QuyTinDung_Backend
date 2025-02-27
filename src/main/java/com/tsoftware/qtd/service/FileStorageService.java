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
    return String.format("%s_%s.%s", fileName, UUID.randomUUID(), getFileExtension(fileName));
  }

  default String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
      return "";
    }
    return fileName.substring(lastIndexOfDot + 1);
  }
}
