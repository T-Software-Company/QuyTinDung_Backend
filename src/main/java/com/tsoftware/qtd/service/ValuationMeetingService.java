package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingDto;
import java.util.List;

public interface ValuationMeetingService {
  ValuationMeetingDto create(ValuationMeetingDto valuationmeetingDto, Long creditId);

  ValuationMeetingDto update(Long id, ValuationMeetingDto valuationmeetingDto);

  void delete(Long id);

  ValuationMeetingDto getById(Long id);

  List<ValuationMeetingDto> getAll();
}
