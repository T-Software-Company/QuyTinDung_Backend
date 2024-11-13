package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.customer.CMNDDto;
import com.tsoftware.qtd.entity.CMND;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CMNDMapper;
import com.tsoftware.qtd.repository.CMNDRepository;
import com.tsoftware.qtd.service.CMNDService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CMNDServiceImpl implements CMNDService {

  @Autowired private CMNDRepository cmndRepository;

  @Autowired private CMNDMapper cmndMapper;

  @Override
  public CMNDDto create(CMNDDto cmndDto) {
    CMND cmnd = cmndMapper.toEntity(cmndDto);
    return cmndMapper.toDto(cmndRepository.save(cmnd));
  }

  @Override
  public CMNDDto update(Long id, CMNDDto cmndDto) {
    CMND cmnd =
        cmndRepository.findById(id).orElseThrow(() -> new NotFoundException("CMND not found"));
    cmndMapper.updateEntity(cmndDto, cmnd);
    return cmndMapper.toDto(cmndRepository.save(cmnd));
  }

  @Override
  public void delete(Long id) {
    cmndRepository.deleteById(id);
  }

  @Override
  public CMNDDto getById(Long id) {
    CMND cmnd =
        cmndRepository.findById(id).orElseThrow(() -> new NotFoundException("CMND not found"));
    return cmndMapper.toDto(cmnd);
  }

  @Override
  public List<CMNDDto> getAll() {
    return cmndRepository.findAll().stream().map(cmndMapper::toDto).collect(Collectors.toList());
  }
}
