package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import java.util.List;

public interface ValuationMeetingService {
  ValuationMeetingResponse create(ValuationMeetingRequest valuationmeetingRequest, Long creditId);

  ValuationMeetingResponse update(Long id, ValuationMeetingRequest valuationmeetingRequest);

  void delete(Long id);

  ValuationMeetingResponse getById(Long id);

  List<ValuationMeetingResponse> getAll();

  void addParticipants(Long id, List<Long> participantIds);

  void removeParticipants(Long id, List<Long> participantIds);
}
