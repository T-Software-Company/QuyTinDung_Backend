package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.GroupDto;
import com.tsoftware.qtd.entity.Group;
import com.tsoftware.qtd.mapper.GroupMapper;
import com.tsoftware.qtd.repository.GroupRepository;
import com.tsoftware.qtd.service.GroupService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  @Autowired private GroupRepository groupRepository;

  @Autowired private GroupMapper groupMapper;

  @Override
  public GroupDto create(GroupDto groupDto) {
    Group group = groupMapper.toEntity(groupDto);
    return groupMapper.toDto(groupRepository.save(group));
  }

  @Override
  public GroupDto update(Long id, GroupDto groupDto) {
    Group group =
        groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
    groupMapper.updateEntity(groupDto, group);
    return groupMapper.toDto(groupRepository.save(group));
  }

  @Override
  public void delete(Long id) {
    groupRepository.deleteById(id);
  }

  @Override
  public GroupDto getById(Long id) {
    Group group =
        groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
    return groupMapper.toDto(group);
  }

  @Override
  public List<GroupDto> getAll() {
    return groupRepository.findAll().stream().map(groupMapper::toDto).collect(Collectors.toList());
  }
}
