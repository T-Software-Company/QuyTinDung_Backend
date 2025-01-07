package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.LegalDocType;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.IdentityInfoDTO;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.service.CustomerService;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.service.KeycloakService;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InitDatabase implements CommandLineRunner {
  private final Keycloak keycloak;
  private final EmployeeRepository employeeRepository;

  private final EmployeeService employeeService;
  private final IdpProperties idpProperties;

  private final KeycloakService keycloakService;
  private final RoleRepository roleRepository;
  private final GroupService groupService;
  private final CustomerService customerService;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    createRoles();
    createAdmin();
    createEmployees();
    createGroups();
    // createCustomers();
  }

  private void createGroups() {
    var groupsResource = keycloak.realm(idpProperties.getRealm()).groups();
    if (!groupsResource.groups().isEmpty()) {
      return;
    }

    var groupRequest =
        GroupRequest.builder()
            .name("Hội đồng tín dụng")
            .roles(List.of(Role.CREDIT_ACCESS.name(), Role.REPORT_ACCESS.name()))
            .build();
    var groupRequest1 =
        GroupRequest.builder()
            .name("Hội đồng định giá")
            .roles(List.of(Role.REPORT_ACCESS.name(), Role.ASSET_VALUATION_ACCESS.name()))
            .build();
    groupService.create(groupRequest);
    groupService.create(groupRequest1);
  }

  private void createAdmin() {
    var userResource = keycloak.realm(idpProperties.getRealm()).users();

    if (!employeeRepository.existsByEmail("admin@gmail.com")
        && userResource.searchByEmail("admin@gmail.com", true).isEmpty()) {
      var employeeRequest =
          EmployeeRequest.builder()
              .email("admin@gmail.com")
              .phone("0834555923")
              .firstName("Nguyễn Văn")
              .lastName("Admin")
              .password("admin")
              .roles(List.of(Role.ADMIN.name(), Role.EMPLOYEE.name()))
              .username("admin")
              .identityInfo(
                  IdentityInfoDTO.builder()
                      .fullName("Nguyễn văn Admin")
                      .gender(Gender.MALE.name())
                      .dateOfBirth(ZonedDateTime.now().minusYears(30))
                      .identifyId("123456789")
                      .legalDocType(LegalDocType.CCCD.name())
                      .expirationDate(ZonedDateTime.now().minusYears(10))
                      .issuingAuthority("Công an TP.HCM")
                      .expirationDate(ZonedDateTime.now().plusYears(10))
                      .build())
              .address(
                  AddressDto.builder()
                      .country("Việt Nam")
                      .cityProvince("Hồ Chí Minh")
                      .district("Quận 1")
                      .wardOrCommune("Phường Bến Nghé")
                      .streetAddress("Đường Nguyễn Huệ")
                      .detail("Số 1, Tầng 2, Tòa nhà ABC")
                      .build())
              .build();
      employeeService.createEmployee(employeeRequest);
    }
  }

  public void createEmployees() {
    var all = employeeRepository.findAll();
    if (all.size() > 1) {
      return;
    }
    final List<String> FIRST_NAMES =
        List.of(
            "Nguyễn", "Trần", "Lê", "Phạm", "Huỳnh", "Hoàng", "Vũ", "Võ", "Đặng", "Bùi", "Đỗ", "Hồ",
            "Ngô", "Dương", "Lý", "Châu", "Cao", "Hà", "Phan", "Tô", "Tôn");

    final List<String> LAST_NAMES =
        List.of(
            "An", "Bình", "Chi", "Dũng", "Hạnh", "Hòa", "Hùng", "Khánh", "Linh", "Long", "Minh",
            "Nam", "Ngọc", "Phương", "Quân", "Quang", "Sơn", "Tâm", "Thành", "Thảo", "Tuấn",
            "Vinh");
    for (int i = 1; i <= 50; i++) {
      Random random = new Random();
      String firstName = FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
      String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
      String email = "employee" + i + "@gmail.com";
      String username = "employee" + i;
      createEmployee(email, username, firstName, lastName);
    }
  }

  private void createEmployee(String email, String username, String firstName, String lastName) {
    var userResource = keycloak.realm(idpProperties.getRealm()).users();

    if (!employeeRepository.existsByEmail(email)
        && userResource.searchByEmail(email, true).isEmpty()) {
      var random = new Random();

      // Generate random identity info
      String fullName = firstName + " " + lastName;
      String identifyId = String.format("%09d", random.nextInt(1000000000));

      // Danh sách các nơi cấp
      List<String> issuingPlaces =
          List.of(
              "TP.HCM",
              "Hà Nội",
              "Đà Nẵng",
              "Cần Thơ",
              "Hải Phòng",
              "Bình Dương",
              "Đồng Nai",
              "Long An",
              "Bà Rịa - Vũng Tàu",
              "Tiền Giang");
      String issuingAuthority =
          "Cục Cảnh sát ĐKQL Cư trú và DLQG về Dân cư - "
              + issuingPlaces.get(random.nextInt(issuingPlaces.size()));

      // Random ngày cấp (3-7 năm trước)
      ZonedDateTime issuingDate = ZonedDateTime.now().minusYears(random.nextInt(4) + 3);

      // Danh sách địa chỉ thường trú
      List<String> permanentAddresses =
          List.of(
              "122/4 Tân Thới Hiệp, Q12, TP.HCM",
              "45/2 Nguyễn Văn Cừ, Q5, TP.HCM",
              "78 Lê Văn Việt, Q9, TP.HCM",
              "234 Lý Thường Kiệt, Q10, TP.HCM",
              "567 Nguyễn Tri Phương, Q5, TP.HCM");

      // Danh sách nơi sinh
      List<String> birthPlaces =
          List.of(
              "TP.HCM",
              "Hà Nội",
              "Đà Nẵng",
              "Cần Thơ",
              "Bình Dương",
              "Đồng Nai",
              "Long An",
              "Tiền Giang");

      var employeeRequest =
          EmployeeRequest.builder()
              .email(email)
              .phone("083" + (random.nextInt(9000000) + 1000000))
              .firstName(firstName)
              .lastName(lastName)
              .password(username)
              .roles(
                  List.of(
                      Arrays.stream(Role.values())
                          .toList()
                          .get(random.nextInt(Role.values().length))
                          .name()))
              .username(username)
              .identityInfo(
                  IdentityInfoDTO.builder()
                      .fullName(fullName)
                      .gender(random.nextBoolean() ? Gender.MALE.name() : Gender.FEMALE.name())
                      .dateOfBirth(ZonedDateTime.now().minusYears(random.nextInt(30) + 20))
                      .identifyId(identifyId)
                      .legalDocType(LegalDocType.CCCD.name())
                      .issuingAuthority(issuingAuthority)
                      .issueDate(issuingDate)
                      .expirationDate(issuingDate.plusYears(15)) // CCCD có giá trị 15 năm
                      .placeOfBirth(birthPlaces.get(random.nextInt(birthPlaces.size())))
                      .permanentAddress(
                          permanentAddresses.get(random.nextInt(permanentAddresses.size())))
                      .nationality("Việt Nam")
                      .ethnicity("Kinh")
                      .build())
              .address(
                  AddressDto.builder()
                      .country("Việt Nam")
                      .cityProvince("Hồ Chí Minh")
                      .wardOrCommune("Phường " + (random.nextInt(20) + 1))
                      .district("Quận " + (random.nextInt(12) + 1))
                      .streetAddress("Đường " + (random.nextInt(50) + 1))
                      .detail("Ngõ " + (random.nextInt(100) + 1))
                      .build())
              .build();
      employeeService.createEmployee(employeeRequest);
    }
  }

  private void createRoles() {
    Arrays.stream(Role.values())
        .forEach(
            role -> {
              if (!roleRepository.existsByName(role.name())) {
                var kcId = keycloakService.createClientRole(role);
                roleRepository.save(
                    com.tsoftware.qtd.entity.Role.builder()
                        .name(role.name())
                        .description(role.getDescription())
                        .kcId(kcId)
                        .build());
              }
            });
  }

  private void createCustomers() throws Exception {
    var all = customerService.getAll();
    if (all.size() > 1) {
      return;
    }
    final List<String> FIRST_NAMES =
        List.of(
            "Nguyễn", "Trần", "Lê", "Phạm", "Huỳnh", "Hoàng", "Vũ", "Võ", "Đặng", "Bùi", "Đỗ", "Hồ",
            "Ngô", "Dương", "Lý", "Châu", "Cao", "Hà", "Phan", "Tô", "Tôn");

    final List<String> LAST_NAMES =
        List.of(
            "An", "Bình", "Chi", "Dũng", "Hạnh", "Hòa", "Hùng", "Khánh", "Linh", "Long", "Minh",
            "Nam", "Ngọc", "Phương", "Quân", "Quang", "Sơn", "Tâm", "Thành", "Thảo", "Tuấn",
            "Vinh");

    for (int i = 1; i <= 50; i++) {
      Random random = new Random();
      String firstName = FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
      String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
      createCustomer(firstName, lastName, "customer" + i + "@gmail.com", "customer" + i);
    }
  }

  private void createCustomer(String firstName, String lastName, String email, String username)
      throws Exception {
    var random = new Random();

    // Generate random identity info
    String fullName = firstName + " " + lastName;
    String identifyId = String.format("%09d", random.nextInt(1000000000));

    // Danh sách các nơi cấp
    List<String> issuingPlaces =
        List.of(
            "TP.HCM",
            "Hà Nội",
            "Đà Nẵng",
            "Cần Thơ",
            "Hải Phòng",
            "Bình Dương",
            "Đồng Nai",
            "Long An",
            "Bà Rịa - Vũng Tàu",
            "Tiền Giang");
    String issuingAuthority =
        "Cục Cảnh sát ĐKQL Cư trú và DLQG về Dân cư - "
            + issuingPlaces.get(random.nextInt(issuingPlaces.size()));

    // Random ngày cấp (3-7 năm trước)
    ZonedDateTime issuingDate = ZonedDateTime.now().minusYears(random.nextInt(4) + 3);

    // Danh sách địa chỉ thường trú
    List<String> permanentAddresses =
        List.of(
            "122/4 Tân Thới Hiệp, Q12, TP.HCM",
            "45/2 Nguyễn Văn Cừ, Q5, TP.HCM",
            "78 Lê Văn Việt, Q9, TP.HCM",
            "234 Lý Thường Kiệt, Q10, TP.HCM",
            "567 Nguyễn Tri Phương, Q5, TP.HCM");

    // Danh sách nơi sinh
    List<String> birthPlaces =
        List.of(
            "TP.HCM",
            "Hà Nội",
            "Đà Nẵng",
            "Cần Thơ",
            "Bình Dương",
            "Đồng Nai",
            "Long An",
            "Tiền Giang");

    var customerResquest =
        CustomerRequest.builder()
            .code(String.valueOf(UUID.randomUUID()))
            .username(username)
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .phone("083" + (random.nextInt(9000000) + 1000000))
            .password(username)
            .enabled(true)
            .identityInfo(
                IdentityInfoDTO.builder()
                    .fullName(fullName)
                    .gender(random.nextBoolean() ? Gender.MALE.name() : Gender.FEMALE.name())
                    .dateOfBirth(ZonedDateTime.now().minusYears(random.nextInt(30) + 20))
                    .identifyId(identifyId)
                    .legalDocType(LegalDocType.CCCD.name())
                    .issuingAuthority(issuingAuthority)
                    .issueDate(issuingDate)
                    .expirationDate(issuingDate.plusYears(15))
                    .placeOfBirth(birthPlaces.get(random.nextInt(birthPlaces.size())))
                    .permanentAddress(
                        permanentAddresses.get(random.nextInt(permanentAddresses.size())))
                    .nationality("Việt Nam")
                    .ethnicity("Kinh")
                    .build())
            .address(
                AddressDto.builder()
                    .country("Việt Nam")
                    .cityProvince("Hồ Chí Minh")
                    .wardOrCommune("Phường " + (random.nextInt(20) + 1))
                    .district("Quận " + (random.nextInt(12) + 1))
                    .streetAddress("Đường " + (random.nextInt(50) + 1))
                    .detail("Ngõ " + (random.nextInt(100) + 1))
                    .build())
            .build();
    customerService.create(customerResquest);
  }
}
