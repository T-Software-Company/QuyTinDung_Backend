package com.tsoftware.qtd.service.Impl;

import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.dto.identity.*;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AddressMapper;
import com.tsoftware.qtd.mapper.EmployeeMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.IdentityClient;
import com.tsoftware.qtd.repository.RoleRepository;
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
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

  EmployeeRepository employeeRepository;
  EmployeeMapper employeeMapper;
  AddressMapper addressMapper;
  IdentityClient identityClient;
  KeycloakService keycloakService;
  RoleRepository roleRepository;

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
  public void createEmployee(EmployeeCreateRequest request) {
    keycloakService.createUser(request);
    employeeRepository.save(employeeMapper.toEmployee(request));
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
  public void updateEmployee(String userId, EmployeeCreateRequest request) {
    keycloakService.updateUser(request, userId);
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setAddress(addressMapper.toAddress(request.getAddress()));
    employee.setUsername(request.getUsername());
    employee.setPhone(request.getPhone());
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setDayOfBirth(request.getSetDayOfBirth());
    employee.setGender(request.getGender());
    employee.setBanned(request.getBanned());
    employee.setRoles(
        request.getRoles().stream()
            .map(r -> roleRepository.findByName(r).orElse(null))
            .collect(Collectors.toList()));
    employeeRepository.save(employee);
  }

  @Override
  public void resetPassword(String userId, String newPassword) {}

  @Override
  public void activateUser(String userId) {}

  @Override
  public void deactivateUser(String userId) {}
}
