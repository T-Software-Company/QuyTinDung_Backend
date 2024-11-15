package com.tsoftware.qtd.service.impl;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
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
}
