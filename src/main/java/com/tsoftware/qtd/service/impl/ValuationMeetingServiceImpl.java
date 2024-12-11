package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.entity.Application;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.ValuationMeeting;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.ValuationMeetingMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.ValuationMeetingRepository;
import com.tsoftware.qtd.service.ValuationMeetingService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValuationMeetingServiceImpl implements ValuationMeetingService {

  @Autowired private ValuationMeetingRepository valuationMeetingRepository;
  @Autowired private ApplicationRepository applicationRepository;
  @Autowired private ValuationMeetingMapper valuationMeetingMapper;

  @Override
  public ValuationMeetingResponse create(
      ValuationMeetingRequest valuationMeetingRequest, UUID creditId) {
    ValuationMeeting valuationMeeting = valuationMeetingMapper.toEntity(valuationMeetingRequest);
    Application application =
        applicationRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    valuationMeeting.setApplication(application);
    return valuationMeetingMapper.toResponse(valuationMeetingRepository.save(valuationMeeting));
  }

  @Override
  public ValuationMeetingResponse update(UUID id, ValuationMeetingRequest valuationMeetingRequest) {
    ValuationMeeting valuationMeeting =
        valuationMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    valuationMeetingMapper.updateEntity(valuationMeetingRequest, valuationMeeting);
    return valuationMeetingMapper.toResponse(valuationMeetingRepository.save(valuationMeeting));
  }

  @Override
  public void delete(UUID id) {
    valuationMeetingRepository.deleteById(id);
  }

  @Override
  public ValuationMeetingResponse getById(UUID id) {
    ValuationMeeting valuationMeeting =
        valuationMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    return valuationMeetingMapper.toResponse(valuationMeeting);
  }

  @Override
  public List<ValuationMeetingResponse> getAll() {
    return valuationMeetingRepository.findAll().stream()
        .map(valuationMeetingMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public void addParticipants(UUID id, List<UUID> participantIds) {
    List<Employee> participants =
        participantIds.stream()
            .map(participantId -> Employee.builder().id(id).build())
            .collect(Collectors.toList());
    ValuationMeeting valuationMeeting =
        valuationMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    valuationMeeting.getParticipants().addAll(participants);
    valuationMeetingRepository.save(valuationMeeting);
  }

  @Override
  public void removeParticipants(UUID id, List<UUID> participantIds) {
    ValuationMeeting valuationMeeting =
        valuationMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    var newParticipants =
        valuationMeeting.getParticipants().stream()
            .filter(v -> !participantIds.contains(v.getId()))
            .collect(Collectors.toList());
    valuationMeeting.setParticipants(newParticipants);
    valuationMeetingRepository.save(valuationMeeting);
  }

  @Override
  public ValuationMeetingResponse getByCreditId(UUID creditId) {
    var valuationMeeting =
        valuationMeetingRepository
            .findByApplicationId(creditId)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    return valuationMeetingMapper.toResponse(valuationMeeting);
  }
}
