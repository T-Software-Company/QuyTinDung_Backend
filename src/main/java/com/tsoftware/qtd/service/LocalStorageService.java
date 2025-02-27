package com.tsoftware.qtd.service;

import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import jakarta.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
public class LocalStorageService implements FileStorageService {

  private final String uploadDir = "documents";

  private final ServletContext servletContext;

  public LocalStorageService(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  @Override
  public String uploadFile(String fileName, InputStream fileInputStream) throws Exception {
    Path uploadPath = Paths.get(uploadDir).normalize();
    Files.createDirectories(uploadPath);

    Path filePath = uploadPath.resolve(fileName);
    Files.copy(fileInputStream, filePath);

    return getFileUrl(fileName);
  }

  @Override
  public InputStream downloadFile(String fileName) throws Exception {
    Path filePath = Paths.get(uploadDir, fileName).normalize();
    if (!Files.exists(filePath)) {
      throw new Exception("File not found");
    }
    return new FileInputStream(filePath.toFile());
  }

  @Override
  public void deleteFile(String fileName) throws Exception {
    Path filePath = Paths.get(uploadDir, fileName).normalize();
    Files.deleteIfExists(filePath);
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

  private String getFileUrl(String fileName) {
    String contextPath = servletContext.getContextPath();
    contextPath = StringUtils.hasText(contextPath) ? contextPath : "";
    return String.format("%s/%s/%s/%s", contextPath, uploadDir, "download", fileName)
        .replaceAll("//+", "/");
  }
}
