package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.GroupMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.GroupRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;

  private final GroupMapper groupMapper;
  private final EmployeeRepository employeeRepository;
  private final KeycloakService keycloakService;
  private final RoleRepository roleRepository;
  private final PageResponseMapper pageResponseMapper;

  public GroupResponse create(GroupRequest groupRequest) {
    Group group = groupMapper.toEntity(groupRequest);
    var kcGroupId = keycloakService.createGroup(groupRequest);
    group.setKcGroupId(kcGroupId);
    var roles = roleRepository.findAllByName(groupRequest.getRoles());
    group.setRoles(roles);
    return groupMapper.toResponse(groupRepository.save(group));
  }

  public GroupResponse update(UUID id, GroupRequest groupRequest) {
    Group group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.updateGroup(groupRequest, group.getKcGroupId());
    groupMapper.updateEntity(groupRequest, group);
    var roles = roleRepository.findAllByName(groupRequest.getRoles());
    group.setRoles(roles);
    return groupMapper.toResponse(groupRepository.save(group));
  }

  public void delete(UUID id) {
    var group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.deleteGroup(group.getKcGroupId());
    groupRepository.deleteById(id);
  }

  public GroupResponse getById(UUID id) {
    Group group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    return groupMapper.toResponse(group);
  }

  public PageResponse<GroupResponse> getAll(Specification<Group> specification, Pageable pageable) {
    try {
      var groups = groupRepository.findAll(specification, pageable).map(groupMapper::toResponse);
      return pageResponseMapper.toPageResponse(groups);
    } catch (DataIntegrityViolationException e) {
      throw new CommonException(ErrorType.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
    }
  }

  public void join(UUID groupId, UUID employeeId) {
    var group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new NotFoundException("Group not found"));
    var employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    var employees = group.getEmployees();
    employees.add(employee);
    group.setEmployees(new ArrayList<>(new HashSet<>(employees)));
    keycloakService.addUserToGroup(group.getKcGroupId(), employee.getUserId());
    groupRepository.save(group);
  }

  public void join(UUID groupId, List<UUID> ids) {
    var group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new NotFoundException("Group not found"));
    var employees = employeeRepository.findByIdIn(ids);

    var employeesOnGroup = group.getEmployees();
    employeesOnGroup.addAll(employees);
    group.setEmployees(new ArrayList<>(employeesOnGroup.stream().distinct().toList()));
    groupRepository.save(group);
    keycloakService.addUserToGroup(
        group.getKcGroupId(), employeesOnGroup.stream().map(Employee::getUserId).toList());
  }

  public void leave(UUID groupId, UUID employeeId) {
    var group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new NotFoundException("Group not found"));
    var employees = group.getEmployees();
    var employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new NotFoundException("Employee not found"));
    employees.remove(employee);
    group.setEmployees(new ArrayList<>(new HashSet<>(employees)));
    keycloakService.removeUserOnGroup(group.getKcGroupId(), employee.getUserId());
    groupRepository.save(group);
  }

  public void leave(UUID groupId, List<UUID> ids) {
    var group =
        groupRepository
            .findById(groupId)
            .orElseThrow(() -> new NotFoundException("Group not found"));
    var employees = employeeRepository.findByIdIn(ids);

    var employeesOnGroup = group.getEmployees();
    employeesOnGroup.removeAll(employees);
    group.setEmployees(new ArrayList<>(new HashSet<>(employeesOnGroup)));
    groupRepository.save(group);
    keycloakService.removeUserOnGroup(
        group.getKcGroupId(), employeesOnGroup.stream().map(Employee::getUserId).toList());
  }

  public void addRoles(UUID id, List<String> roles) {
    var group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.addRolesToGroup(group.getKcGroupId(), roles);
    var rolesEntities = roleRepository.findAllByName(roles);
    group.getRoles().addAll(rolesEntities);
    group.setRoles(new HashSet<>(group.getRoles()).stream().toList());
    groupRepository.save(group);
  }

  public void removeRoles(UUID id, List<String> roles) {
    var group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.removeRolesOnGroup(group.getKcGroupId(), roles);
    var rolesEntities = roleRepository.findAllByName(roles);
    group.getRoles().removeAll(rolesEntities);
    groupRepository.save(group);
  }
}
