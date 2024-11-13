package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.employee.GroupDto;
import java.util.List;

public interface GroupService {
  GroupDto create(GroupDto groupDto);

  GroupDto update(Long id, GroupDto groupDto);

  void delete(Long id);

  GroupDto getById(Long id);

  List<GroupDto> getAll();
}
