package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
public class CustomerRequest {
  @NotNull String fullName;
  @Email String email;
  String phone;
  String note;
  Gender gender;
  String status;
  CMNDRequest cmnd;
  CCCDRequest cccd;
  PassPortRequest passPort;
  AddressDto address;
  MultipartFile signaturePhotoFile;
}
