package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.constants.EnumType.Banned;
import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AddressMapper;
import com.tsoftware.qtd.mapper.EmployeeMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.KeycloakService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional()
public class EmployeeServiceImpl implements EmployeeService {

  EmployeeRepository employeeRepository;
  EmployeeMapper employeeMapper;
  AddressMapper addressMapper;
  KeycloakService keycloakService;

  @Override
  public List<EmployeeResponse> getEmployees() {
    return employeeRepository.findAll().stream()
        .map(employeeMapper::toEmployeeResponse)
        .collect(Collectors.toList());
  }

  @Override
  public EmployeeResponse getProfile() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var profile =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    return employeeMapper.toEmployeeResponse(profile);
  }

  @Override
  public void createEmployee(EmployeeRequest request) {
    var userId = keycloakService.createUser(request);
    var employee = employeeMapper.toEmployee(request);
    employee.setUserId(userId);
    employeeRepository.save(employee);
  }

  @Override
  public void updateProfile(ProfileRequest request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    keycloakService.updateUser(request, userId);
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setEmail(request.getEmail());
    employee.setPhone(request.getPhone());
    employee.setDayOfBirth(request.getSetDayOfBirth());
    employeeRepository.save(employee);
  }

  @Override
  public void updateEmployee(String userId, EmployeeRequest request) {
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    keycloakService.updateUser(request, userId);

    if (request.getAddress() != null) {
      employee.setAddress(addressMapper.toAddress(request.getAddress()));
    }
    if (request.getUsername() != null) {
      employee.setUsername(request.getUsername());
    }
    if (request.getEmail() != null) {
      employee.setEmail(request.getEmail());
    }
    if (request.getPhone() != null) {
      employee.setPhone(request.getPhone());
    }
    if (request.getFirstName() != null) {
      employee.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null) {
      employee.setLastName(request.getLastName());
    }
    if (request.getDayOfBirth() != null) {
      employee.setDayOfBirth(request.getDayOfBirth());
    }
    if (request.getGender() != null) {
      employee.setGender(request.getGender());
    }
    if (request.getRoles() != null) {
      employee.setRoles(
          request.getRoles().stream().map(Role::valueOf).collect(Collectors.toList()));
    }
    employeeRepository.save(employee);
  }

  @Override
  public void resetPassword(String userId, String newPassword) {
    keycloakService.resetPassword(userId, newPassword);
  }

  @Override
  public void activeEmployee(String userId) {
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setBanned(Banned.ACTIVE);
    keycloakService.activeUser(userId);
    employeeRepository.save(employee);
  }

  @Override
  public void deactivateEmployee(String userId) {
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setBanned(Banned.LOCKED);
    keycloakService.deactivateUser(userId);
    employeeRepository.save(employee);
  }
}
