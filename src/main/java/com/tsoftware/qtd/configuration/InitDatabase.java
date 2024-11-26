package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.address.AddressDto;
import com.tsoftware.qtd.dto.employee.EmployeeRequest;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.KeycloakService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    createRoles();
    createAdmin();
    createEmployees();
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
              .banned(Banned.ACTIVE)
              .dayOfBirth(ZonedDateTime.of(2000, 10, 12, 0, 0, 0, 0, ZoneId.systemDefault()))
              .gender(Gender.MALE.name())
              .status(EmploymentStatus.WORKING.name())
              .password("admin")
              .roles(List.of(Role.ADMIN, Role.EMPLOYEE))
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
    for (int i = 1; i <= 50; i++) {
      String email = "employee" + i + "@gmail.com";
      String username = "employee" + i;
      createEmployee(email, username);
    }
  }

  private void createEmployee(String email, String username) {
    var userResource = keycloak.realm(idpProperties.getRealm()).users();

    if (!employeeRepository.existsByEmail(email)
        && userResource.searchByEmail(email, true).isEmpty()) {
      var random = new Random();
      var employeeRequest =
          EmployeeRequest.builder()
              .email(email)
              .phone("083" + (random.nextInt(9000000) + 1000000))
              .firstName("Nguyễn Văn")
              .lastName("Ngẫu Nhiên")
              .banned(Banned.ACTIVE)
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
              .status(EmploymentStatus.WORKING.name())
              .password(username)
              .roles(List.of(Role.EMPLOYEE))
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
