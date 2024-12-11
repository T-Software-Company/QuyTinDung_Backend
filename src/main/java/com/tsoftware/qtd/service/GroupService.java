package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface GroupService {
  GroupResponse create(GroupRequest groupRequest);

  GroupResponse update(UUID id, GroupRequest groupRequest);

  void delete(UUID id);

  GroupResponse getById(UUID id);

  Page<GroupResponse> getAll(Specification<Group> specification, Pageable pageable);

  void join(UUID groupId, UUID employeeId);

  void leave(UUID groupId, UUID employeeId);

  void addRoles(UUID id, List<String> roles);

  void removeRoles(UUID id, List<String> roles);
}
