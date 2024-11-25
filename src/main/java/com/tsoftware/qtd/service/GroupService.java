package com.tsoftware.qtd.service;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.employee.GroupRequest;
import com.tsoftware.qtd.dto.employee.GroupResponse;
import com.tsoftware.qtd.entity.Group;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface GroupService {
  GroupResponse create(GroupRequest groupRequest);

  GroupResponse update(Long id, GroupRequest groupRequest);

  void delete(Long id);

  GroupResponse getById(Long id);

  Page<GroupResponse> getAll(Specification<Group> specification, Pageable pageable);

  void join(Long groupId, Long employeeId);

  void leave(Long groupId, Long employeeId);

  void addRoles(Long id, List<Role> roles);

  void removeRoles(Long id, List<Role> roles);
}
