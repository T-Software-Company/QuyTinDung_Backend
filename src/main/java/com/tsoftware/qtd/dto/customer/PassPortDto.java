package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.PassPortType;
import com.tsoftware.qtd.entity.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public class PassPortDto {

    @NotNull(message = "ID_REQUIRED")
    Long id;

    @NotNull(message = "CREATED_AT_REQUIRED")
    ZonedDateTime createdAt;

    @NotNull(message = "UPDATED_AT_REQUIRED")
    ZonedDateTime updatedAt;

    String lastModifiedBy;
    String createdBy;

    @Valid
    @NotNull(message = "CUSTOMER_REQUIRED")
    Customer customer;

    @NotBlank(message = "FULL_NAME_REQUIRED")
    @Size(min = 3, max = 100, message = "FULL_NAME_SIZE")
    String fullName;

    @NotNull(message = "GENDER_REQUIRED")
    Gender gender;

    @NotNull(message = "DATE_OF_BIRTH_REQUIRED")
    @Past(message = "DATE_OF_BIRTH_MUST_BE_IN_PAST")
    ZonedDateTime dateOfBirth;

    @NotBlank(message = "NATIONALITY_REQUIRED")
    String nationality;

    @NotBlank(message = "PLACE_OF_BIRTH_REQUIRED")
    String placeOfBirth;

    @NotBlank(message = "PERMANENT_ADDRESS_REQUIRED")
    String permanentAddress;

    @NotNull(message = "ISSUE_DATE_REQUIRED")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime issueDate;

    @NotNull(message = "EXPIRATION_DATE_REQUIRED")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime expirationDate;

    @NotBlank(message = "ISSUING_AUTHORITY_REQUIRED")
    String issuingAuthority;

    @NotBlank(message = "FRONT_PHOTO_URL_REQUIRED")
    String frontPhotoURL;

    @NotBlank(message = "BACK_PHOTO_URL_REQUIRED")
    String backPhotoURL;

    @NotNull(message = "PASS_PORT_TYPE_REQUIRED")
    PassPortType passPortType;
}
