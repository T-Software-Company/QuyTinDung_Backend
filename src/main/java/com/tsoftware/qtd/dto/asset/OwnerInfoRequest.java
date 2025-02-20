package com.tsoftware.qtd.dto.asset;

import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OwnerInfoRequest {
  @NotBlank(message = "Full name cannot be blank")
  @Size(max = 255, message = "Full name cannot exceed 255 characters")
  private String fullName;

  @NotNull(message = "Day of birth cannot be null")
  @PastOrPresent(message = "Day of birth must be in the past or present")
  private ZonedDateTime dayOfBirth;

  @NotBlank(message = "ID card number cannot be blank")
  @Pattern(regexp = "^[0-9]{9,12}$", message = "ID card number must be between 9 and 12 digits")
  private String idCardNumber;

  @NotBlank(message = "Permanent address cannot be blank")
  @Size(max = 500, message = "Permanent address cannot exceed 500 characters")
  private String permanentAddress;
}
