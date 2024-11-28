package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.exception.SpringFilterBadRequestException;
import com.tsoftware.qtd.mapper.GroupMapper;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.GroupRepository;
import com.tsoftware.qtd.repository.RoleRepository;
import com.tsoftware.qtd.service.GroupService;
import com.tsoftware.qtd.service.KeycloakService;
import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupServiceImpl implements GroupService {

  private GroupRepository groupRepository;

  private GroupMapper groupMapper;
  private final EmployeeRepository employeeRepository;
  private final KeycloakService keycloakService;
  private final RoleRepository roleRepository;

  @Override
  public GroupResponse create(GroupRequest groupRequest) {
    Group group = groupMapper.toEntity(groupRequest);
    var kcGroupId = keycloakService.createGroup(groupRequest);
    group.setKcGroupId(kcGroupId);
    var roles = roleRepository.findAllByName(groupRequest.getRoles());
    group.setRoles(roles);
    return groupMapper.toResponse(groupRepository.save(group));
  }

  @Override
  public GroupResponse update(Long id, GroupRequest groupRequest) {
    Group group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.updateGroup(groupRequest, group.getKcGroupId());
    groupMapper.updateEntity(groupRequest, group);
    var roles = roleRepository.findAllByName(groupRequest.getRoles());
    group.setRoles(roles);
    return groupMapper.toResponse(groupRepository.save(group));
  }

  @Override
  public void delete(Long id) {
    var group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.deleteGroup(group.getKcGroupId());
    groupRepository.deleteById(id);
  }

  @Override
  public GroupResponse getById(Long id) {
    Group group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    return groupMapper.toResponse(group);
  }

  @Override
  public Page<GroupResponse> getAll(Specification<Group> specification, Pageable pageable) {
    try {
      return groupRepository.findAll(specification, pageable).map(groupMapper::toResponse);
    } catch (DataIntegrityViolationException e) {
      throw new SpringFilterBadRequestException(e.getMessage());
    }
  }

  @Override
  public void join(Long groupId, Long employeeId) {
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
    keycloakService.addUserToGroup(group.getKcGroupId(), employee.getUserId());
    groupRepository.save(group);
  }

  @Override
  public void leave(Long groupId, Long employeeId) {
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
    keycloakService.removeUserOnGroup(group.getKcGroupId(), employee.getUserId());
    groupRepository.save(group);
  }

  @Override
  public void addRoles(Long id, List<String> roles) {
    var group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.addRolesToGroup(group.getKcGroupId(), roles);
    var rolesEntities = roleRepository.findAllByName(roles);
    group.getRoles().addAll(rolesEntities);
    group.setRoles(new HashSet<>(group.getRoles()).stream().toList());
    groupRepository.save(group);
  }

  @Override
  public void removeRoles(Long id, List<String> roles) {
    var group =
        groupRepository.findById(id).orElseThrow(() -> new NotFoundException("Group not found"));
    keycloakService.removeRolesOnGroup(group.getKcGroupId(), roles);
    var rolesEntities = roleRepository.findAllByName(roles);
    group.getRoles().removeAll(rolesEntities);
    groupRepository.save(group);
  }
}
