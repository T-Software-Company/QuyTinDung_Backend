package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Banned;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public class EmployeeDto {

    @NotNull(message = "ID_REQUIRED")
    Long id;

    @NotNull(message = "CREATED_AT_REQUIRED")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime createdAt;

    @NotNull(message = "UPDATED_AT_REQUIRED")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime updatedAt;

    String lastModifiedBy;
    String createdBy;

    @NotNull(message = "EMPLOYEE_CODE_REQUIRED")
    Long employeeCode;

    @NotBlank(message = "EMPLOYEE_EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL_FORMAT")
    String employeeEmail;

    @NotBlank(message = "FIRST_NAME_REQUIRED")
    @Size(min = 2, max = 50, message = "INVALID_FIRST_NAME_LENGTH")
    String firstName;

    @NotBlank(message = "LAST_NAME_REQUIRED")
    @Size(min = 2, max = 50, message = "INVALID_LAST_NAME_LENGTH")
    String lastName;

    @NotBlank(message = "ADDRESS_REQUIRED")
    String address;

    @NotNull(message = "BANNED_STATUS_REQUIRED")
    Banned banned;

    @NotNull(message = "DAY_OF_BIRTH_REQUIRED")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    ZonedDateTime dayOfBirth;

    @NotBlank(message = "PHONE_REQUIRED")
    @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "INVALID_PHONE_FORMAT")
    String phone;  // Changed Long to String to support long phone numbers and special characters like '+'
}
