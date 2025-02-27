package com.tsoftware.qtd.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class GoogleCloudStorageService implements FileStorageService {

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

  @Override
  public String uploadFile(String fileName, InputStream fileInputStream) throws Exception {

    Bucket bucket = storage.get(bucketName);
    Blob blob = bucket.create(fileName, fileInputStream, "application/octet-stream");

    return blob.getMediaLink();
  }

  @Override
  public InputStream downloadFile(String fileName) throws Exception {
    Blob blob = storage.get(bucketName, fileName);
    if (blob == null) {
      throw new Exception("File not found");
    }
    return new ByteArrayInputStream(blob.getContent());
  }

  @Override
  public void deleteFile(String fileName) {
    Blob blob = storage.get(bucketName, fileName);
    if (blob != null) {
      blob.delete();
    }
  }

  @Override
  public String upload(MultipartFile file) {
    try {
      String fileName = getFileName(file.getOriginalFilename());
      return uploadFile(fileName, file.getInputStream());
    } catch (Exception e) {
      log.error("Error uploading file ", e);
      throw new CommonException(ErrorType.UNEXPECTED, "Error uploading file.");
    }
  }
}
