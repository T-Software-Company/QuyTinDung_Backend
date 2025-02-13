package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.approval.ApprovalResponse;
import com.tsoftware.qtd.entity.Approval;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ApprovalMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.ApproveRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalService {

  private final ApproveRepository approveRepository;
  private final PageResponseMapper pageResponseMapper;
  private final ApprovalMapper approvalMapper;

  public ApprovalResponse create(ApprovalResponse approvalResponse) {
    Approval approval = approvalMapper.toEntity(approvalResponse);
    return approvalMapper.toResponse(approveRepository.save(approval));
  }

  public ApprovalResponse update(UUID id, ApprovalResponse approvalResponse) {
    Approval approval =
        approveRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Approval not found"));
    approvalMapper.updateEntity(approvalResponse, approval);
    return approvalMapper.toResponse(approveRepository.save(approval));
  }

  public void delete(UUID id) {
    approveRepository.deleteById(id);
  }

  public ApprovalResponse getById(UUID id) {
    Approval approval =
        approveRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Approval not found"));
    return approvalMapper.toResponse(approval);
  }

  public List<ApprovalResponse> getAll() {
    return approveRepository.findAll().stream()
        .map(approvalMapper::toResponse)
        .collect(Collectors.toList());
  }

  public PageResponse<ApprovalResponse> getAll(Specification<Approval> spec, Pageable pageable) {
    var result = approveRepository.findAll(spec, pageable).map(approvalMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }
}
