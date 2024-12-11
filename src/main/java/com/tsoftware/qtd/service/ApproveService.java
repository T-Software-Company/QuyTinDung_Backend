package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.ApproveResponse;
import java.util.List;
import java.util.UUID;

public interface ApproveService {
  ApproveResponse create(ApproveResponse approveResponse);

  ApproveResponse update(UUID id, ApproveResponse approveResponse);

  void delete(UUID id);

  ApproveResponse getById(UUID id);

  List<ApproveResponse> getAll();

  List<ApproveResponse> getByApproverId(UUID id);
}
