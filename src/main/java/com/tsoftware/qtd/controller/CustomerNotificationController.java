package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.service.CustomerNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer-notifications")
@RequiredArgsConstructor
public class CustomerNotificationController {

  private final CustomerNotificationService customerNotificationService;
}
