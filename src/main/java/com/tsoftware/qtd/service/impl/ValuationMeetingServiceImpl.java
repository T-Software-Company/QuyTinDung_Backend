package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.entity.Credit;
import com.tsoftware.qtd.entity.Employee;
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
  public ValuationMeetingResponse create(
      ValuationMeetingRequest valuationmeetingRequest, Long creditId) {
    ValuationMeeting valuationmeeting = valuationmeetingMapper.toEntity(valuationmeetingRequest);
    Credit credit =
        creditRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    valuationmeeting.setCredit(credit);
    return valuationmeetingMapper.toResponse(valuationmeetingRepository.save(valuationmeeting));
  }

  @Override
  public ValuationMeetingResponse update(Long id, ValuationMeetingRequest valuationmeetingRequest) {
    ValuationMeeting valuationmeeting =
        valuationmeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    valuationmeetingMapper.updateEntity(valuationmeetingRequest, valuationmeeting);
    return valuationmeetingMapper.toResponse(valuationmeetingRepository.save(valuationmeeting));
  }

  @Override
  public void delete(Long id) {
    valuationmeetingRepository.deleteById(id);
  }

  @Override
  public ValuationMeetingResponse getById(Long id) {
    ValuationMeeting valuationmeeting =
        valuationmeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    return valuationmeetingMapper.toResponse(valuationmeeting);
  }

  @Override
  public List<ValuationMeetingResponse> getAll() {
    return valuationmeetingRepository.findAll().stream()
        .map(valuationmeetingMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public void addParticipants(Long id, List<Long> participantIds) {
    List<Employee> participants =
        participantIds.stream()
            .map(participantId -> Employee.builder().id(id).build())
            .collect(Collectors.toList());
    ValuationMeeting valuationMeeting =
        valuationmeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    valuationMeeting.getParticipants().addAll(participants);
    valuationmeetingRepository.save(valuationMeeting);
  }

  @Override
  public void removeParticipants(Long id, List<Long> participantIds) {
    ValuationMeeting valuationMeeting =
        valuationmeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    var newParticipants =
        valuationMeeting.getParticipants().stream()
            .filter(v -> !participantIds.contains(v.getId()))
            .collect(Collectors.toList());
    valuationMeeting.setParticipants(newParticipants);
    valuationmeetingRepository.save(valuationMeeting);
  }
}
