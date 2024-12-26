package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.exception.*;
import com.tsoftware.qtd.mapper.EmployeeMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.GroupRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.service.EmployeeService;
import com.tsoftware.qtd.service.KeycloakService;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final KeycloakService keycloakService;
  private final RoleRepository roleRepository;
  private final GroupRepository groupRepository;

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
    var employee = employeeMapper.toEmployee(request);
    if (request.getRoles() != null) {
      request
          .getRoles()
          .forEach(
              role -> {
                if (!roleRepository.existsByName(role)) {
                  throw new CommonException(ErrorType.ENTITY_NOT_FOUND, "Role: " + role);
                }
              });
      var rolesExists = roleRepository.findAllByName(request.getRoles());
      employee.setRoles(rolesExists);
    }

    var userId = keycloakService.createUser(request);
    employee.setUserId(userId);
    employee.setEnabled(true);
    var employeeSaved = employeeRepository.save(employee);
    if (employee.getGroups() != null) {
      employee
          .getGroups()
          .forEach(
              group -> {
                if (!groupRepository.existsById(group.getId())) {
                  throw new CommonException(ErrorType.ENTITY_NOT_FOUND, "Group: " + group);
                }
              });
      var groupExists =
          groupRepository.findAllById(
              employee.getGroups().stream().map(Group::getId).collect(Collectors.toList()));
      groupExists.forEach(
          group -> {
            group.getEmployees().add(employee);
            groupRepository.save(group);
          });
    }
    return employeeMapper.toEmployeeResponse(employeeSaved);
  }

  @Override
  public EmployeeResponse updateProfile(ProfileRequest request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    keycloakService.updateUser(request, userId);
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setEmail(request.getEmail());
    employee.setPhone(request.getPhone());
    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

  @Override
  public EmployeeResponse updateEmployee(UUID id, EmployeeUpdateRequest request) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    keycloakService.updateUser(request, employee.getUserId());
    employeeMapper.updateEntity(request, employee);
    var roles = roleRepository.findAllByName(request.getRoles());
    employee.setRoles(roles);
    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

  @Override
  public void resetPassword(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, "employee: " + id));
    keycloakService.resetPasswordByEmail(employee.getUserId());
  }

  @Override
  public void enable(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(true);
    keycloakService.activeUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  @Override
  public void disable(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(false);
    keycloakService.deactivateUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  @Override
  public EmployeeResponse getEmployee(UUID id) {

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
  public void addRoles(UUID id, List<String> roles) {
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
  public void removeRoles(UUID id, List<String> roles) {
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
  public void disables(List<UUID> ids) {
    ids.forEach(this::disable);
  }

  @Override
  public void enables(List<UUID> ids) {
    ids.forEach(this::enable);
  }

  @Override
  public void delete(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(false);
    employee.setIsDeleted(true);
    keycloakService.deactivateUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  @Override
  public void delete(List<UUID> ids) {
    ids.forEach(this::delete);
  }

  @Override
  public Set<Employee> findByUserIdIn(Set<String> assignees) {
    return employeeRepository.findByUserIdIn(assignees);
  }
}
