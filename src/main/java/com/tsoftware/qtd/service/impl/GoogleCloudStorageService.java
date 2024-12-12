package com.tsoftware.qtd.service.impl;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
public class GoogleCloudStorageService {

  @Value("${google.cloud.storage.bucket-name}")
  private String bucketName;

  private final Storage storage;

  public GoogleCloudStorageService(
      @Value("${google.cloud.storage.service-account}") String serviceAccount) throws IOException {
    storage =
        StorageOptions.newBuilder()
            .setCredentials(
                ServiceAccountCredentials.fromStream(new FileInputStream(serviceAccount)))
            .build()
            .getService();
  }

  public String uploadFile(String fileName, InputStream fileInputStream) throws Exception {

    Bucket bucket = storage.get(bucketName);
    Blob blob = bucket.create(fileName, fileInputStream, "application/octet-stream");

    return blob.getMediaLink();
  }

  public InputStream downloadFile(String fileName) throws Exception {
    Blob blob = storage.get(bucketName, fileName);
    if (blob == null) {
      throw new Exception("File not found");
    }
    return new ByteArrayInputStream(blob.getContent());
  }

  public void deleteFile(String fileName) {
    Blob blob = storage.get(bucketName, fileName);
    if (blob != null) {
      blob.delete();
    }
  }

  public String upload(MultipartFile file) {
    try {
      String fileName = getFileName(file.getOriginalFilename());
      return uploadFile(fileName, file.getInputStream());
    } catch (Exception e) {
      log.error("Error uploading file ", e);
      throw new CommonException(ErrorType.UNEXPECTED, "Error uploading file.");
    }
  }

  private String getFileName(String fileName) {
    return String.format("%s_%s.%s", fileName, UUID.randomUUID(), getFileExtension(fileName));
  }

  private String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
      return "";
    }
    return fileName.substring(lastIndexOfDot + 1);
  }
}
