package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import com.tsoftware.qtd.entity.Approve;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApproveMapper;
import com.tsoftware.qtd.repository.ApproveRepository;
import com.tsoftware.qtd.service.ApproveService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApproveServiceImpl implements ApproveService {

  private final ApproveRepository approveRepository;

  private final ApproveMapper approveMapper;

  @Override
  public ApproveResponse create(ApproveResponse approveResponse) {
    Approve approve = approveMapper.toEntity(approveResponse);
    return approveMapper.toDTO(approveRepository.save(approve));
  }

  @Override
  public ApproveResponse update(UUID id, ApproveResponse approveResponse) {
    Approve approve =
        approveRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Approve not found"));
    approveMapper.updateEntity(approveResponse, approve);
    return approveMapper.toDTO(approveRepository.save(approve));
  }

  @Override
  public void delete(UUID id) {
    approveRepository.deleteById(id);
  }

  @Override
  public ApproveResponse getById(UUID id) {
    Approve approve =
        approveRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Approve not found"));
    return approveMapper.toDTO(approve);
  }

  @Override
  public List<ApproveResponse> getAll() {
    return approveRepository.findAll().stream()
        .map(approveMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<ApproveResponse> getByApproverId(UUID id) {
    var approves = approveRepository.findByApproverId(id);
    return approves.stream().map(approveMapper::toDTO).collect(Collectors.toList());
  }
}
