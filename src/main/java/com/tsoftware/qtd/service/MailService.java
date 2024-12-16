package com.tsoftware.qtd.service;

import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {
  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;

  @Async
  void sendEmail(
      String to, String subject, String name, String content, Map<String, InputStream> files) {
    try {
      var message = mailSender.createMimeMessage();
      var helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setTo(to);
      helper.setSubject(subject);
      var context = new Context();
      Map<String, Object> variables = new HashMap<>();
      variables.put("content", content);
      variables.put("companyName", "Company Name");
      variables.put("recipientName", name);
      variables.put("contactUsLink", "https://tsoftware.com/contact-us");
      variables.put("privacyPolicyLink", "https://tsoftware.com/privacy-policy");
      context.setVariables(variables);
      var html = templateEngine.process("email-template", context);
      helper.setText(html, true);

      files.forEach(
          (fileName, inputStream) -> {
            try {
              helper.addAttachment(
                  fileName, new ByteArrayDataSource(inputStream, "application/octet-stream"));
            } catch (MessagingException | IOException e) {
              // We need custom an exception for email sending
              throw new RuntimeException(e);
            }
          });

      mailSender.send(message);

    } catch (MessagingException e) {
      // We need custom an exception for email sending
      throw new RuntimeException(e);
    }
  }

  @Async
  void sendEmail(String to, String subject, String name, String content) {
    sendEmail(to, subject, name, content, new HashMap<>());
  }
}
