package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.entity.CCCD;
import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.repository.LoanPlanRepository;
import com.tsoftware.qtd.service.impl.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

  @Mock LoanPlanRepository loanPlanRepository;

  @Test
  public void objectToMapstruct() throws IllegalAccessException {
    Long id = 1L;
    var cccd = CCCD.builder().id(id).gender(Gender.FEMALE).nationality("Kinh").build();
    var customer =
        Customer.builder()
            .id(id)
            .fullName("Nguyen van Tuan")
            .email("tuan@gmail.com")
            .phone("20232092390")
            .cccd(cccd)
            .build();
    var loanPlan = LoanPlan.builder().note("note").id(id).customer(customer).build();

    var map = DocumentService.objectToMapStructForReplace(loanPlan, 3);

    map.get("id");
  }
}
