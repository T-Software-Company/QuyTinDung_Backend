package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressVm;
import com.tsoftware.qtd.dto.appraisalPlan.AppraisalPlanDto;
import com.tsoftware.qtd.dto.asset.AssetDto;
import com.tsoftware.qtd.dto.debtNotification.DebtNotificationDto;
import com.tsoftware.qtd.dto.incomeProof.IncomeProofDto;
import com.tsoftware.qtd.dto.loan.LoanDto;
import com.tsoftware.qtd.dto.loan.LoanPlanDto;
import com.tsoftware.qtd.dto.loan.LoanRequestDto;
import com.tsoftware.qtd.entity.Address;
import com.tsoftware.qtd.entity.AppraisalReport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CustomerDto {

  @NotNull(message = "CUSTOMER_ID_REQUIRED")
  Long customerId;

  @NotBlank(message = "FULL_NAME_REQUIRED")
  @Size(min = 3, max = 100, message = "FULL_NAME_SIZE")
  String fullName;

  @NotBlank(message = "EMAIL_REQUIRED")
  @Email(message = "INVALID_EMAIL_FORMAT")
  String email;

  @Valid  // Ensures validation on AddressVm
  private AddressVm address;

  @Valid  // Ensures validation on LoanPlanDto
  private LoanPlanDto loanPlan;

  @Valid  // Ensures validation on each LoanDto in the list
  private List<LoanDto> loan;

  @Valid  // Ensures validation on each LoanRequestDto in the list
  private List<LoanRequestDto> loanRequests;

  @Valid  // Ensures validation on each DebtNotificationDto in the list
  private List<DebtNotificationDto> debtNotifications;

  @Valid  // Ensures validation on PassPortDto
  private PassPortDto passPort;

  @Valid  // Ensures validation on CCCDDto
  private CCCDDto cccd;

  @Valid  // Ensures validation on CMNDDto
  private CMNDDto cmnd;

  @Valid  // Ensures validation on each IncomeProofDto in the list
  private List<IncomeProofDto> incomeProof;

  @Valid  // Ensures validation on AppraisalReport
  private AppraisalReport appraisalReport;

  @Valid  // Ensures validation on AppraisalPlanDto
  private AppraisalPlanDto appraisalPlan;

  @Valid  // Ensures validation on each AssetDto in the list
  private List<AssetDto> asset;

  @NotNull(message = "PHONE_REQUIRED")
  @Pattern(regexp = "^[0-9]{10,15}$", message = "INVALID_PHONE_FORMAT")
  String phone;  // Changed to String for phone number flexibility

  @Size(max = 500, message = "NOTE_TOO_LONG")
  String note;

  String signaturePhoto;

  @NotNull(message = "GENDER_REQUIRED")
  Gender gender;

  @NotBlank(message = "STATUS_REQUIRED")
  @Size(min = 3, max = 20, message = "STATUS_SIZE")
  String status;
}
