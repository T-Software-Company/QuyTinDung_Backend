package com.tsoftware.qtd.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tsoftware.qtd.service.impl.GoogleCloudStorageService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GoogleCloudStorageService.class})
public class GoogleCloudStorageServiceIntegrationTest {

  @Autowired GoogleCloudStorageService googleCloudStorageService;
  private String testFileName;

  @Test
  public void testUploadFile() throws Exception {
    String fileName = "testFile.txt";
    InputStream fileInputStream =
        new ByteArrayInputStream("Nội dung kiểm thử".getBytes(StandardCharsets.UTF_8));
    String mediaLink = googleCloudStorageService.uploadFile(fileName, fileInputStream);
    assertNotNull(mediaLink);
    System.out.println("File uploaded successfully. Media link: " + mediaLink);
  }

  @Test
  public void testDownloadFile_FileExists() throws Exception {
    String fileName = "named420d5a0-f2d6-459c-9b1b-3fb76ae56f52.jfif"; // file exists on bucket
    InputStream downloadedFile = googleCloudStorageService.downloadFile(fileName);
    assertNotNull(downloadedFile);
    System.out.println("File downloaded successfully.");
  }

  @Test
  public void testDownloadFile_FileDoesNotExist() {
    String fileName = "nonexistentFile.txt";
    assertThrows(Exception.class, () -> googleCloudStorageService.downloadFile(fileName));
  }

  @AfterEach
  public void tearDown() {
    String fileName = "testFile.txt";
    googleCloudStorageService.deleteFile(fileName);
    System.out.println("File deleted successfully after test.");
  }
}
