package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.service.EmployeeNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeenotifications")
@RequiredArgsConstructor
public class EmployeeNotificationController {

  private final EmployeeNotificationService employeenotificationService;
}
