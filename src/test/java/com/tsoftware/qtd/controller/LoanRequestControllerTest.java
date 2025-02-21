package com.tsoftware.qtd.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.application.LoanRequestRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanRequestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testCreateLoanRequest() throws Exception {
    LoanRequestRequest loanRequestRequest =
        LoanRequestRequest.builder()
            .purpose("Purchase of machinery")
            .amount(new BigDecimal("50000.00"))
            .borrowerType(BorrowerType.INDIVIDUAL.name())
            .loanSecurityType(LoanSecurityType.MORTGAGE.name())
            .loanCollateralTypes(List.of("VEHICLE"))
            .note("Urgent loan for business expansion")
            .metadata(
                Map.of(
                    "key1", "value1",
                    "key2", "value2"))
            .application(ApplicationRequest.builder().id("{{applicationId}}").build())
            .build();
    mockMvc
        .perform(
            post("/api/loan-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanRequestRequest)))
        .andExpect(status().isOk());
  }
}
