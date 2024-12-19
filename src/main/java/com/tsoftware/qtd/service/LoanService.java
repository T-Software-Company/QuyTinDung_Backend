package com.tsoftware.qtd.service;

import com.tsoftware.commonlib.util.RandomUtil;
import com.tsoftware.qtd.constants.EnumType.DisbursementStatus;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.constants.EnumType.RepaymentStatus;
import com.tsoftware.qtd.dto.application.DisbursementDTO;
import com.tsoftware.qtd.dto.loan.DisbursementDetail;
import com.tsoftware.qtd.dto.loan.LoanInfo;
import com.tsoftware.qtd.dto.loan.RepaymentScheduleDTO;
import com.tsoftware.qtd.dto.loan.SignRequestDetail;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.Disbursement;
import com.tsoftware.qtd.entity.LoanAccount;
import com.tsoftware.qtd.entity.RepaymentSchedule;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.DtoMapper;
import com.tsoftware.qtd.repository.LoanAccountRepository;
import com.tsoftware.qtd.validation.BulkDisbursementValidator;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoanService {
  @Value("${t-software.account.format.loan:LN****###**}")
  private String accountFormat;

  @Value("${t-software.max-retry:10}")
  private int maxRetry;

  @Resource LoanAccountRepository loanAccountRepository;

  @Resource BulkDisbursementValidator validator;
  @Resource DtoMapper mapper;

  public Disbursement createDisbursement(LoanAccount loan) {

    return Disbursement.builder()
        .loanAccount(loan)
        .amount(loan.getApprovedAmount())
        .disbursementDate(ZonedDateTime.now())
        .status(DisbursementStatus.PENDING)
        .build();
  }

  public List<Disbursement> createBulkDisbursement(
      LoanAccount loan, List<DisbursementDetail> disbursementDetails) {

    // Validate loan status
    if (loan.getApplication().getStatus() != LoanStatus.SIGNED) {
      throw new InvalidOperationException("Loan must be approved for disbursement");
    }

    // Validate bulk disbursement request
    validator.validate(loan, disbursementDetails);

    return disbursementDetails.stream()
        .map(
            detail ->
                Disbursement.builder()
                    .loanAccount(loan)
                    .amount(detail.getAmount())
                    .disbursementDate(detail.getDisbursementDate())
                    .description(detail.getDescription())
                    .status(DisbursementStatus.PENDING)
                    .build())
        .collect(Collectors.toList());
  }

  private List<RepaymentSchedule> generateRepaymentSchedule(LoanAccount loan) {
    List<RepaymentSchedule> repaymentSchedule = new ArrayList<>();

    BigDecimal monthlyInterestRate =
        loan.getInterestRate().divide(BigDecimal.valueOf(12 * 100), 6, RoundingMode.HALF_UP);

    // Calculate EMI using the formula: EMI = P * r * (1 + r)^n / ((1 + r)^n - 1)
    BigDecimal principalAmount = loan.getApprovedAmount();
    double rateDouble = monthlyInterestRate.doubleValue();
    int terms = loan.getTermInMonths();

    double emiDouble =
        (principalAmount.doubleValue() * rateDouble * Math.pow(1 + rateDouble, terms))
            / (Math.pow(1 + rateDouble, terms) - 1);

    BigDecimal emi = BigDecimal.valueOf(emiDouble).setScale(2, RoundingMode.HALF_UP);

    ZonedDateTime currentDate = ZonedDateTime.now();
    BigDecimal remainingPrincipal = principalAmount;

    for (int i = 1; i <= terms; i++) {
      RepaymentSchedule installment = new RepaymentSchedule();
      installment.setLoanAccount(loan);
      installment.setInstallmentNumber(i);
      installment.setDueDate(currentDate.plusMonths(i));

      // Calculate interest for this month
      BigDecimal interest =
          remainingPrincipal.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);

      // Principal for this month is EMI - Interest
      BigDecimal principal = emi.subtract(interest);

      // For the last installment, adjust to clear any remaining principal
      if (i == terms) {
        principal = remainingPrincipal;
        installment.setTotalAmount(principal.add(interest));
      } else {
        installment.setTotalAmount(emi);
        remainingPrincipal = remainingPrincipal.subtract(principal);
      }

      installment.setPrincipalAmount(principal);
      installment.setInterestAmount(interest);
      installment.setStatus(RepaymentStatus.PENDING);

      repaymentSchedule.add(installment);
    }

    return repaymentSchedule;
  }

  public LoanAccount createLoanAccount(Application entity, SignRequestDetail signRequestDetail) {
    LoanInfo loanInfo = signRequestDetail.getLoanInfo();
    LoanAccount loanAccount =
        LoanAccount.builder()
            .application(entity)
            .approvedAmount(loanInfo.getApprovedAmount())
            .interestRate(loanInfo.getInterestRate())
            .termInMonths(loanInfo.getTermInMonths())
            .disbursedAmount(BigDecimal.ZERO)
            .accountNumber(generateAccountNumber())
            .build();

    List<DisbursementDetail> disbursements = signRequestDetail.getDisbursements();
    List<Disbursement> disbursementEntities = new ArrayList<>();
    if (CollectionUtils.isEmpty(disbursements)) {
      disbursementEntities.add(createDisbursement(loanAccount));
    } else {
      disbursementEntities.addAll(createBulkDisbursement(loanAccount, disbursements));
    }

    List<RepaymentSchedule> repaymentSchedules = generateRepaymentSchedule(loanAccount);

    loanAccount.setDisbursements(disbursementEntities);
    loanAccount.setRepaymentSchedule(repaymentSchedules);
    return loanAccountRepository.save(loanAccount);
  }

  public String generateAccountNumber() {
    String accountNumber;
    int retry = 0;
    do {
      if (retry++ > maxRetry) {
        throw new CommonException(
            ErrorType.METHOD_ARGUMENT_NOT_VALID, "Failed to generate unique account number.");
      }
      accountNumber = RandomUtil.randomWithFormat(accountFormat);
    } while (loanAccountRepository.existsByAccountNumber(accountNumber));

    return accountNumber;
  }

  public List<DisbursementDTO> getDisbursements(String accountNumber) {
    return loanAccountRepository
        .findByAccountNumber(accountNumber)
        .map(loanAccount -> mapper.toListDisbursement(loanAccount.getDisbursements()))
        .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, accountNumber));
  }

  public List<RepaymentScheduleDTO> getRepaymentSchedule(String accountNumber) {
    return loanAccountRepository
        .findByAccountNumber(accountNumber)
        .map(loanAccount -> mapper.toListRepaymentSchedule(loanAccount.getRepaymentSchedule()))
        .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, accountNumber));
  }
}
