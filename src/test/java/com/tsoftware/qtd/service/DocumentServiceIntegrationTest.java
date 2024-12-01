package com.tsoftware.qtd.service;

import static org.junit.jupiter.api.Assertions.*;

import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.entity.LoanPlan;
import com.tsoftware.qtd.service.impl.DocumentService;
import com.tsoftware.qtd.service.impl.GoogleCloudStorageService;
import java.io.*;
import java.util.Iterator;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DocumentService.class, GoogleCloudStorageService.class})
@Slf4j
public class DocumentServiceIntegrationTest {

  @Autowired private DocumentService documentService;
  @Autowired private GoogleCloudStorageService googleCloudStorageService;

  @Test
  public void objectToMapstruct() throws IllegalAccessException {
    var id = UUID.randomUUID();
    var customer =
        Customer.builder()
            .id(id)
            .fullName("Nguyen van Tuan")
            .email("tuan@gmail.com")
            .phone("20232092390")
            .build();
    var loanPlan = LoanPlan.builder().note("note").id(id).customer(customer).build();

    var map = DocumentService.objectToMapStructForReplace(loanPlan, 3);
    map.get("");
    assertEquals(map.get("note"), "note");
    assertEquals(map.get("customer.cccd.nationality"), "Kinh");
    assertEquals(map.get("customer.id"), "1");
  }

  //  @Test
  ////  public void replace() throws Exception {
  ////    var loanPlan =
  ////        LoanPlan.builder()
  ////            .monthlyIncome(BigDecimal.valueOf(1200000))
  ////            .totalCapitalRequirement(BigDecimal.valueOf(100000000))
  ////            .ownCapital(BigDecimal.valueOf(50000000))
  ////            .proposedLoanAmount(BigDecimal.valueOf(50000000))
  ////            .repaymentPlan("Thanh toán mỗi tháng theo lãi suất cố định")
  ////            .loanNeeds("Mua xe")
  ////            .loanTerm("12 Tháng")
  ////            .customer(
  ////                Customer.builder()
  ////                    .cccd(
  ////                        CCCD.builder()
  ////                            .fullName("Nguyễn Văn Tuấn") // Họ tên
  ////                            .dateOfBirth(
  ////                                ZonedDateTime.of(
  ////                                    LocalDate.parse("1999-02-03").atStartOfDay(),
  ////                                    ZoneId.systemDefault()))
  ////                            .issueDate(
  ////                                ZonedDateTime.of(
  ////                                    LocalDate.parse("2021-04-02").atStartOfDay(),
  ////                                    ZoneId.systemDefault()))
  ////                            .issuingAuthority("Công an Nghệ An")
  ////                            .identifyId("12982389290")
  ////                            .build())
  ////                    .address(
  ////                        Address.builder()
  ////                            .streetAddress("Số 123, Đường Nguyễn Văn Cừ")
  ////                            .cityProvince("Thành phố Vinh")
  ////                            .country("Việt Nam")
  ////                            .district("Quận Hà Đông")
  ////                            .detail("Căn hộ số 3, tầng 2, tòa nhà ABC")
  ////                            .wardOrCommune("Phường 1")
  ////                            .build())
  ////                    .build())
  ////            .build();
  //
  //    var templateUrl = "phuong-an-vay-von-the-chap-chung-cu.doc"; // template is existing
  //    var templateFile = googleCloudStorageService.downloadFile(templateUrl);
  //    InputStream fileReplaced = documentService.replace(loanPlan, templateFile, 3);
  //    var text = extractTextFromInputStreamDocument(fileReplaced);
  //    log.info("docFile: {}", text);
  //
  //    assertTrue(text.contains(loanPlan.getLoanNeeds()));
  //    assertTrue(text.contains(loanPlan.getRepaymentPlan()));
  //    assertTrue(text.contains(loanPlan.getLoanTerm()));
  //    assertTrue(text.contains(String.valueOf(loanPlan.getMonthlyIncome())));
  //    assertTrue(text.contains(String.valueOf(loanPlan.getTotalCapitalRequirement())));
  //    assertTrue(text.contains(String.valueOf(loanPlan.getOwnCapital())));
  //    assertTrue(text.contains(String.valueOf(loanPlan.getProposedLoanAmount())));
  //    assertTrue(text.contains(loanPlan.getCustomer().getCccd().getFullName()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getCccd().getIssuingAuthority()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getCccd().getIdentifyId()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getAddress().getDetail()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getAddress().getCityProvince()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getAddress().getDistrict()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getAddress().getCountry()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getAddress().getWardOrCommune()));
  //    assertTrue(text.contains(loanPlan.getCustomer().getAddress().getStreetAddress()));
  //  }

  private String extractTextFromInputStreamDocument(InputStream inputStream) throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    try (BufferedInputStream bis = new BufferedInputStream(inputStream)) {
      bis.mark(4);
      byte[] header = new byte[4];
      bis.read(header);
      bis.reset();

      if (documentService.isDocFile(header)) {
        // Handle .doc (HWPFDocument)
        try (HWPFDocument doc = new HWPFDocument(bis)) {
          Range range = doc.getRange();
          stringBuilder.append(range.text());
        }
      } else {
        // Handle .docx (XWPFDocument)
        try (XWPFDocument docx = new XWPFDocument(bis)) {
          Iterator<XWPFParagraph> paragraphs = docx.getParagraphsIterator();
          while (paragraphs.hasNext()) {
            XWPFParagraph paragraph = paragraphs.next();
            for (XWPFRun run : paragraph.getRuns()) {
              String text = run.getText(0);
              if (text != null) {
                stringBuilder.append(text);
              }
            }
          }
        }
      }
    }

    return stringBuilder.toString();
  }
}
