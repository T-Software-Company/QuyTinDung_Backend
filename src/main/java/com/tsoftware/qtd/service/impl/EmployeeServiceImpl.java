package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.exception.SpringFilterBadRequestException;
import com.tsoftware.qtd.mapper.EmployeeMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.KeycloakService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
  KeycloakService keycloakService;
  private final RoleRepository roleRepository;

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
  public EmployeeResponse createEmployee(EmployeeRequest request) {
    var userId = keycloakService.createUser(request);
    var employee = employeeMapper.toEmployee(request);
    employee.setUserId(userId);
    var rolesExists = roleRepository.findAllByName(request.getRoles());
    employee.setRoles(rolesExists);
    employee.setEnabled(true);
    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

  @Override
  public EmployeeResponse updateProfile(ProfileRequest request) {
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
    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

  @Override
  public EmployeeResponse updateEmployee(String userId, EmployeeRequest request) {
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    keycloakService.updateUser(request, userId);
    employeeMapper.updateEntity(request, employee);

    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

  @Override
  public void resetPassword(String userId, String newPassword) {
    keycloakService.resetPassword(userId, newPassword);
  }

  @Override
  public void enable(Long id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(true);
    keycloakService.activeUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  @Override
  public void disable(Long id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(false);
    keycloakService.deactivateUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  @Override
  public EmployeeResponse getEmployee(Long id) {

    return employeeMapper.toEmployeeResponse(
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found")));
  }

  @Override
  public Page<EmployeeResponse> getAll(Specification<Employee> spec, Pageable page) {
    try {
      return employeeRepository.findAll(spec, page).map(employeeMapper::toEmployeeResponse);
    } catch (DataIntegrityViolationException e) {
      throw new SpringFilterBadRequestException(e.getMessage());
    }
  }

  @Override
  public void addRoles(Long id, List<String> roles) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    keycloakService.addRolesToUser(employee.getUserId(), roles);
    var roleEntities = roleRepository.findAllByName(roles);
    employee.setRoles(roleEntities);
    employeeRepository.save(employee);
  }

  @Override
  public void removeRoles(Long id, List<String> roles) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    keycloakService.removeRolesOnUser(employee.getUserId(), roles);
    var roleEntities = roleRepository.findAllByName(roles);
    employee.getRoles().removeAll(roleEntities);
    employeeRepository.save(employee);
  }

  @Override
  public void disables(List<Long> ids) {
    ids.forEach(this::disable);
  }

  @Override
  public void enables(List<Long> ids) {
    ids.forEach(this::enable);
  }
}
