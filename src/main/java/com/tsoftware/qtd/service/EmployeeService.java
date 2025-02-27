package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.employee.*;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.exception.*;
import com.tsoftware.qtd.mapper.EmployeeMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.GroupRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.util.RequestUtil;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final KeycloakService keycloakService;
  private final RoleRepository roleRepository;
  private final GroupRepository groupRepository;
  private final PageResponseMapper pageResponseMapper;

  public EmployeeResponse getProfile() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var profile =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    return employeeMapper.toEmployeeResponse(profile);
  }

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

  public EmployeeResponse updateProfile(ProfileRequest request) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    keycloakService.updateUser(request, userId);
    employeeMapper.updateEntity(request, employee);
    return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
  }

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

  public void changePassword(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, "employee: " + id));
    keycloakService.changePasswordByEmail(employee.getUserId());
  }

  public void enable(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(true);
    keycloakService.activeUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  public void disable(UUID id) {
    var employee =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employee.setEnabled(false);
    keycloakService.deactivateUser(employee.getUserId());
    employeeRepository.save(employee);
  }

  public EmployeeResponse getEmployee(UUID id) {

    return employeeMapper.toEmployeeResponse(
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found")));
  }

  public PageResponse<EmployeeResponse> getAll(Specification<Employee> spec, Pageable page) {
    try {
      var employees =
          employeeRepository.findAll(spec, page).map(employeeMapper::toEmployeeResponse);
      return pageResponseMapper.toPageResponse(employees);
    } catch (DataIntegrityViolationException e) {
      throw new CommonException(ErrorType.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
    }
  }

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

  public void disables(List<UUID> ids) {
    ids.forEach(this::disable);
  }

  public void enables(List<UUID> ids) {
    ids.forEach(this::enable);
  }

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

  public void delete(List<UUID> ids) {
    ids.forEach(this::delete);
  }

  public Set<Employee> findByUserIdIn(Set<String> assignees) {
    return employeeRepository.findByUserIdIn(assignees);
  }

  public void changeAvatar(String url) {
    var userId = RequestUtil.getUserId();
    var employee =
        employeeRepository
            .findByUserId(userId)
            .orElseThrow(
                () -> new CommonException(ErrorType.ENTITY_NOT_FOUND, "employeeUserId: " + userId));
    employee.setAvatarUrl(url);
    employeeRepository.save(employee);
  }
}
