package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.entity.Approval;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApproveMapper;
import com.tsoftware.qtd.repository.ApproveRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApproveService {

  private final ApproveRepository approveRepository;

  private final ApproveMapper approveMapper;

  public ApprovalResponse create(ApprovalResponse approvalResponse) {
    Approval approval = approveMapper.toEntity(approvalResponse);
    return approveMapper.toDTO(approveRepository.save(approval));
  }

  public ApprovalResponse update(UUID id, ApprovalResponse approvalResponse) {
    Approval approval =
        approveRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Approval not found"));
    approveMapper.updateEntity(approvalResponse, approval);
    return approveMapper.toDTO(approveRepository.save(approval));
  }

  public void delete(UUID id) {
    approveRepository.deleteById(id);
  }

  public ApprovalResponse getById(UUID id) {
    Approval approval =
        approveRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Approval not found"));
    return approveMapper.toDTO(approval);
  }

  public List<ApprovalResponse> getAll() {
    return approveRepository.findAll().stream()
        .map(approveMapper::toDTO)
        .collect(Collectors.toList());
  }

  public List<ApprovalResponse> getByApproverId(UUID id) {
    var approves = approveRepository.findByApproverId(id);
    return approves.stream().map(approveMapper::toDTO).collect(Collectors.toList());
  }
}
