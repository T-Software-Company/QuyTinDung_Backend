package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import java.util.List;
import java.util.UUID;

public interface ValuationMeetingService {
  ValuationMeetingResponse create(ValuationMeetingRequest valuationmeetingRequest, UUID creditId);

  ValuationMeetingResponse update(UUID id, ValuationMeetingRequest valuationmeetingRequest);

  void delete(UUID id);

  ValuationMeetingResponse getById(UUID id);

  List<ValuationMeetingResponse> getAll();

  void addParticipants(UUID id, List<UUID> participantIds);

  void removeParticipants(UUID id, List<UUID> participantIds);

  ValuationMeetingResponse getByCreditId(UUID creditId);
}
