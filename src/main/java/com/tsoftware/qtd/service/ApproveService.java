package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.ApproveResponse;
import java.util.List;

public interface ApproveService {
  ApproveResponse create(ApproveResponse approveResponse);

  ApproveResponse update(Long id, ApproveResponse approveResponse);

  void delete(Long id);

  ApproveResponse getById(Long id);

  List<ApproveResponse> getAll();

  List<ApproveResponse> getByApproverId(Long id);
}
