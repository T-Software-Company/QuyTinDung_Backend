package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.Valuation.ValuationReportRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationReportResponse;
import java.util.List;

public interface ValuationReportService {
  ValuationReportResponse create(ValuationReportRequest valuationreportRequest);

  ValuationReportResponse update(Long id, ValuationReportRequest valuationreportRequest);

  void delete(Long id);

  ValuationReportResponse getById(Long id);

  List<ValuationReportResponse> getAll();

  List<ApproveResponse> addApprove(Long id, List<Long> approverIds);

  void removeApprove(Long id, List<Long> approverIds);
}
