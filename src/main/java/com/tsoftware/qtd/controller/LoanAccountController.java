package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.dto.loan.RepaymentScheduleDTO;
import com.tsoftware.qtd.service.LoanService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan-accounts")
public class LoanAccountController {

  @Resource LoanService loanService;

  @GetMapping("/{accountNumber}/disbursements")
  public ResponseEntity<List<DisbursementDTO>> getDisbursements(
      @PathVariable String accountNumber) {
    return ResponseEntity.ok(loanService.getDisbursements(accountNumber));
  }

  @GetMapping("/{accountNumber}/repayment-schedule")
  public ResponseEntity<List<RepaymentScheduleDTO>> getRepaymentSchedule(
      @PathVariable String accountNumber) {
    return ResponseEntity.ok(loanService.getRepaymentSchedule(accountNumber));
  }
}
