package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import com.tsoftware.qtd.dto.transaction.ApproveResponse;
import java.util.List;
import java.util.UUID;

public interface ValuationReportService {
  ValuationReportResponse create(ValuationReportRequest valuationreportRequest);

  ValuationReportResponse update(UUID id, ValuationReportRequest valuationreportRequest);

  void delete(UUID id);

  ValuationReportResponse getById(UUID id);

  List<ValuationReportResponse> getAll();

  List<ApproveResponse> addApprove(UUID id, List<UUID> approverIds);

  void removeApprove(UUID id, List<UUID> approverIds);
}
