package com.tsoftware.qtd.service;

import com.tsoftware.qtd.service.impl.GoogleCloudStorageService;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest()
public class MailServiceTest {
  @Autowired MailService mailService;
  @Autowired GoogleCloudStorageService googleCloudStorageService;

  @Test
  public void sendMail() throws Exception {
    String fileName = "named420d5a0-f2d6-459c-9b1b-3fb76ae56f52.jfif"; // file exists on bucket
    InputStream downloadedFile = googleCloudStorageService.downloadFile(fileName);
    Map<String, InputStream> files = new HashMap<>();
    files.put(fileName, downloadedFile);
    mailService.sendEmail(
        "duncan.nguyen.18@gmail.com", "dong sai gon", "tuan", "this is content", files);
    log.info("email sent");
  }
}
