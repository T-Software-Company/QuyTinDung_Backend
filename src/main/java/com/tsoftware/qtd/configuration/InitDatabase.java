package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.service.KeycloakService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Transactional
public class InitDatabase implements CommandLineRunner {
  private final Keycloak keycloak;
  private final EmployeeRepository employeeRepository;

  private final EmployeeService employeeService;
  private final IdpProperties idpProperties;

  private final KeycloakService keycloakService;
  private final RoleRepository roleRepository;
  private final GroupService groupService;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    createRoles();
    createAdmin();
    createEmployees();
    createGroups();
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
              .dayOfBirth(ZonedDateTime.of(2000, 10, 12, 0, 0, 0, 0, ZoneId.systemDefault()))
              .gender(Gender.MALE.name())
              .password("admin")
              .roles(List.of(Role.ADMIN.name(), Role.EMPLOYEE.name()))
              .username("admin")
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
      var employeeRequest =
          EmployeeRequest.builder()
              .email(email)
              .phone("083" + (random.nextInt(9000000) + 1000000))
              .firstName(firstName)
              .lastName(lastName)
              .dayOfBirth(
                  ZonedDateTime.of(
                      1980 + random.nextInt(20),
                      random.nextInt(12) + 1,
                      random.nextInt(28) + 1,
                      0,
                      0,
                      0,
                      0,
                      ZoneId.systemDefault()))
              .gender(random.nextBoolean() ? Gender.MALE.name() : Gender.FEMALE.name())
              .password(username)
              .code(String.valueOf(UUID.randomUUID()))
              .roles(
                  List.of(
                      Arrays.stream(Role.values())
                          .toList()
                          .get(random.nextInt(Role.values().length))
                          .name()))
              .username(username)
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
}
