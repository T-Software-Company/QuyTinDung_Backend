package com.tsoftware.qtd.controller.docs;

import com.tsoftware.qtd.constants.EnumType.Gender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import java.lang.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Operation(
    summary = "Create a new customer",
    requestBody =
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content =
                @Content(
                    mediaType = "multipart/form-data",
                    schemaProperties = {
                      // CustomerRequest fields
                      @SchemaProperty(
                          name = "fullName",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Full name of the customer",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "email",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "email",
                                  description = "Email address")),
                      @SchemaProperty(
                          name = "phone",
                          schema = @Schema(type = "string", description = "Phone number")),
                      @SchemaProperty(
                          name = "note",
                          schema = @Schema(type = "string", description = "Additional notes")),
                      @SchemaProperty(
                          name = "gender",
                          schema =
                              @Schema(
                                  implementation = Gender.class,
                                  description = "Gender (e.g., MALE, FEMALE)")),
                      @SchemaProperty(
                          name = "status",
                          schema =
                              @Schema(type = "string", description = "Status of the customer")),
                      @SchemaProperty(
                          name = "signaturePhotoFile",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Signature photo file")),

                      // AddressDto fields
                      @SchemaProperty(
                          name = "address.country",
                          schema = @Schema(type = "string", description = "Country")),
                      @SchemaProperty(
                          name = "address.cityProvince",
                          schema = @Schema(type = "string", description = "City or Province")),
                      @SchemaProperty(
                          name = "address.district",
                          schema = @Schema(type = "string", description = "District")),
                      @SchemaProperty(
                          name = "address.wardOrCommune",
                          schema = @Schema(type = "string", description = "Ward or Commune")),
                      @SchemaProperty(
                          name = "address.streetAddress",
                          schema = @Schema(type = "string", description = "Street address")),
                      @SchemaProperty(
                          name = "address.detail",
                          schema = @Schema(type = "string", description = "Detailed address")),

                      // CMNDRequest fields (updated)
                      @SchemaProperty(
                          name = "cmnd.identifyId",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Identification ID",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "cmnd.fullName",
                          schema = @Schema(type = "string", description = "Full name on CMND")),
                      @SchemaProperty(
                          name = "cmnd.gender",
                          schema =
                              @Schema(
                                  implementation = Gender.class,
                                  description = "Gender on CMND")),
                      @SchemaProperty(
                          name = "cmnd.dateOfBirth",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Date of birth")),
                      @SchemaProperty(
                          name = "cmnd.placeOfBirth",
                          schema = @Schema(type = "string", description = "Place of birth")),
                      @SchemaProperty(
                          name = "cmnd.issueDate",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Date of issue")),
                      @SchemaProperty(
                          name = "cmnd.issuePlace",
                          schema = @Schema(type = "string", description = "Place of issue")),
                      @SchemaProperty(
                          name = "cmnd.ethnicity",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Ethnicity",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "cmnd.religion",
                          schema = @Schema(type = "string", description = "Religion")),
                      @SchemaProperty(
                          name = "cmnd.frontPhotoFile",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Front photo of CMND")),
                      @SchemaProperty(
                          name = "cmnd.backPhotoURL",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Back photo of CMND")),

                      // CCCDRequest fields (updated)
                      @SchemaProperty(
                          name = "cccd.identifyId",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Identification ID",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "cccd.fullName",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Full name",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "cccd.gender",
                          schema = @Schema(implementation = Gender.class, description = "Gender")),
                      @SchemaProperty(
                          name = "cccd.dateOfBirth",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Date of birth")),
                      @SchemaProperty(
                          name = "cccd.nationality",
                          schema = @Schema(type = "string", description = "Nationality")),
                      @SchemaProperty(
                          name = "cccd.issueDate",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Date of issue")),
                      @SchemaProperty(
                          name = "cccd.expirationDate",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Expiration date")),
                      @SchemaProperty(
                          name = "cccd.issuePlace",
                          schema = @Schema(type = "string", description = "Place of issue")),
                      @SchemaProperty(
                          name = "cccd.frontPhotoFile",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Front photo of CCCD")),
                      @SchemaProperty(
                          name = "cccd.backPhotoURL",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Back photo of CCCD")),

                      // PassPortRequest fields (updated)
                      @SchemaProperty(
                          name = "passPort.identifyId",
                          schema = @Schema(type = "string", description = "Passport ID")),
                      @SchemaProperty(
                          name = "passPort.fullName",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Full name on passport",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "passPort.gender",
                          schema =
                              @Schema(
                                  implementation = Gender.class,
                                  description = "Gender on passport")),
                      @SchemaProperty(
                          name = "passPort.dateOfBirth",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Date of birth on passport")),
                      @SchemaProperty(
                          name = "passPort.passPortType",
                          schema =
                              @Schema(
                                  type = "string",
                                  description = "Passport type",
                                  requiredMode = Schema.RequiredMode.REQUIRED)),
                      @SchemaProperty(
                          name = "passPort.nationality",
                          schema = @Schema(type = "string", description = "Nationality")),
                      @SchemaProperty(
                          name = "passPort.issueDate",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Date of issue")),
                      @SchemaProperty(
                          name = "passPort.expirationDate",
                          schema =
                              @Schema(
                                  type = "string",
                                  format = "date-time",
                                  description = "Expiration date")),
                      @SchemaProperty(
                          name = "passPort.issuePlace",
                          schema = @Schema(type = "string", description = "Place of issue")),
                      @SchemaProperty(
                          name = "passPort.frontPhotoFile",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Front photo of passport")),
                      @SchemaProperty(
                          name = "passPort.backPhotoURL",
                          schema =
                              @Schema(
                                  implementation = MultipartFile.class,
                                  description = "Back photo of passport"))
                    })))
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerCreateSg {}
