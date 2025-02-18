package com.tsoftware.qtd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
  @MessageMapping("/message")
  @SendToUser("/queue/notification")
  public ResponseEntity<?> sendMessage(@Payload String message) {
    return ResponseEntity.ok("hello from server");
  }
}
