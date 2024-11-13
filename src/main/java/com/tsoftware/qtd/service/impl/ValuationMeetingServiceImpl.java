package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingDto;
import com.tsoftware.qtd.entity.Credit;
import com.tsoftware.qtd.entity.ValuationMeeting;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ValuationMeetingMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.repository.ValuationMeetingRepository;
import com.tsoftware.qtd.service.ValuationMeetingService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValuationMeetingServiceImpl implements ValuationMeetingService {

  @Autowired private ValuationMeetingRepository valuationmeetingRepository;
  @Autowired private CreditRepository creditRepository;
  @Autowired private ValuationMeetingMapper valuationmeetingMapper;

  @Override
  public ValuationMeetingDto create(ValuationMeetingDto valuationmeetingDto, Long creditId) {
    ValuationMeeting valuationmeeting = valuationmeetingMapper.toEntity(valuationmeetingDto);
    Credit credit =
        creditRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    valuationmeeting.setCredit(credit);
    return valuationmeetingMapper.toDto(valuationmeetingRepository.save(valuationmeeting));
  }

  @Override
  public ValuationMeetingDto update(Long id, ValuationMeetingDto valuationmeetingDto) {
    ValuationMeeting valuationmeeting =
        valuationmeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    valuationmeetingMapper.updateEntity(valuationmeetingDto, valuationmeeting);
    return valuationmeetingMapper.toDto(valuationmeetingRepository.save(valuationmeeting));
  }

  @Override
  public void delete(Long id) {
    valuationmeetingRepository.deleteById(id);
  }

  @Override
  public ValuationMeetingDto getById(Long id) {
    ValuationMeeting valuationmeeting =
        valuationmeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    return valuationmeetingMapper.toDto(valuationmeeting);
  }

  @Override
  public List<ValuationMeetingDto> getAll() {
    return valuationmeetingRepository.findAll().stream()
        .map(valuationmeetingMapper::toDto)
        .collect(Collectors.toList());
  }
}
