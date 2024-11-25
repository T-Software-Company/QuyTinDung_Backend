// package com.tsoftware.qtd.configuration;
//
// import com.tsoftware.qtd.constants.EnumType.Banned;
// import com.tsoftware.qtd.constants.EnumType.EmploymentStatus;
// import com.tsoftware.qtd.constants.EnumType.Gender;
// import com.tsoftware.qtd.constants.EnumType.Role;
// import com.tsoftware.qtd.dto.address.AddressDto;
// import com.tsoftware.qtd.dto.employee.EmployeeRequest;
// import com.tsoftware.qtd.repository.EmployeeRepository;
// import com.tsoftware.qtd.service.EmployeeService;
// import java.time.ZoneId;
// import java.time.ZonedDateTime;
// import java.util.List;
// import java.util.Random;
// import org.keycloak.admin.client.Keycloak;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.transaction.annotation.Transactional;
//
// @Configuration
// public class InitDatabase implements CommandLineRunner {
//  @Autowired private Keycloak keycloak;
//  @Autowired private EmployeeRepository employeeRepository;
//
//  @Autowired private EmployeeService employeeService;
//  @Autowired private IdpProperties idpProperties;
//
//  @Override
//  @Transactional
//  public void run(String... args) throws Exception {
//    createAdmin();
//    createEmployees();
//  }
//
//  private void createAdmin() {
//    var userResource = keycloak.realm(idpProperties.getRealm()).users();
//    if (!employeeRepository.existsByEmail("admin@gmail.com")
//        && userResource.searchByEmail("", true).isEmpty()) {
//      var employeeRequest =
//          EmployeeRequest.builder()
//              .email("admin@gmail.com")
//              .phone("0834555923")
//              .firstName("Nguyễn Văn")
//              .lastName("Admin")
//              .banned(Banned.ACTIVE)
//              .dayOfBirth(ZonedDateTime.of(2000, 10, 12, 0, 0, 0, 0, ZoneId.systemDefault()))
//              .gender(Gender.MALE)
//              .employmentStatus(EmploymentStatus.WORKING)
//              .password("admin")
//              .roles(List.of(Role.ADMIN, Role.EMPLOYEE))
//              .username("admin")
//              .address(
//                  AddressDto.builder()
//                      .country("Việt Nam")
//                      .cityProvince("Hồ Chí Minh")
//                      .wardOrCommune("P")
//                      .district("")
//                      .streetAddress("")
//                      .detail("")
//                      .build())
//              .build();
//      employeeService.createEmployee(employeeRequest);
//    }
//  }
//
//  public void createEmployees() {
//    for (int i = 1; i <= 50; i++) {
//      String email = "employee" + i + "@gmail.com";
//      String username = "employee" + i;
//      createEmployee(email, username);
//    }
//  }
//
//  public void createEmployee(String email, String username) {
//    var userResource = keycloak.realm(idpProperties.getRealm()).users();
//
//    if (!employeeRepository.existsByEmail(email)
//        && userResource.searchByEmail("", true).isEmpty()) {
//      var random = new Random();
//      var employeeRequest =
//          EmployeeRequest.builder()
//              .email(email)
//              .phone("083" + (random.nextInt(9000000) + 1000000))
//              .firstName("Nguyễn Văn")
//              .lastName("Ngẫu Nhiên")
//              .banned(Banned.ACTIVE)
//              .dayOfBirth(
//                  ZonedDateTime.of(
//                      1980 + random.nextInt(20),
//                      random.nextInt(12) + 1,
//                      random.nextInt(28) + 1,
//                      0,
//                      0,
//                      0,
//                      0,
//                      ZoneId.systemDefault()))
//              .gender(random.nextBoolean() ? Gender.MALE : Gender.FEMALE)
//              .employmentStatus(EmploymentStatus.WORKING)
//              .password(username)
//              .roles(List.of(Role.EMPLOYEE))
//              .username(username)
//              .address(
//                  AddressDto.builder()
//                      .country("Việt Nam")
//                      .cityProvince("Hồ Chí Minh")
//                      .wardOrCommune("Phường " + (random.nextInt(20) + 1))
//                      .district("Quận " + (random.nextInt(12) + 1))
//                      .streetAddress("Đường " + (random.nextInt(50) + 1))
//                      .detail("Ngõ " + (random.nextInt(100) + 1))
//                      .build())
//              .build();
//      employeeService.createEmployee(employeeRequest);
//    }
//  }
// }
