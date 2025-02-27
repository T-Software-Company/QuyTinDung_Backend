package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.entity.ValuationMeeting;
import com.tsoftware.qtd.event.ValuationMeetingCreatedEvent;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.mapper.ValuationMeetingMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
import com.tsoftware.qtd.repository.ValuationMeetingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValuationMeetingService {

  private final ValuationMeetingRepository valuationMeetingRepository;
  private final ApplicationRepository applicationRepository;
  private final ValuationMeetingMapper valuationMeetingMapper;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final EmployeeRepository employeeRepository;
  private final PageResponseMapper pageResponseMapper;

  public ValuationMeetingResponse create(
      ValuationMeetingRequest valuationMeetingRequest, UUID applicationId) {
    ValuationMeeting valuationMeeting = valuationMeetingMapper.toEntity(valuationMeetingRequest);
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "Application not found: " + applicationId));
    valuationMeeting.setApplication(application);
    var savedEmployee = new ArrayList<Employee>();
    valuationMeeting
        .getParticipants()
        .forEach(
            e -> {
              var employee =
                  employeeRepository
                      .findById(e.getId())
                      .orElseThrow(
                          () ->
                              new CommonException(
                                  ErrorType.ENTITY_NOT_FOUND, "employee: " + e.getId()));
              savedEmployee.add(employee);
            });
    valuationMeeting.setParticipants(savedEmployee);
    var saved = valuationMeetingRepository.save(valuationMeeting);
    var result = valuationMeetingMapper.toResponse(saved);
    applicationEventPublisher.publishEvent(new ValuationMeetingCreatedEvent(this, result));
    return result;
  }

  public ValuationMeetingResponse update(UUID id, ValuationMeetingRequest valuationMeetingRequest) {
    ValuationMeeting valuationMeeting =
        valuationMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    valuationMeetingMapper.updateEntity(valuationMeetingRequest, valuationMeeting);
    return valuationMeetingMapper.toResponse(valuationMeetingRepository.save(valuationMeeting));
  }

  public void delete(UUID id) {
    valuationMeetingRepository.deleteById(id);
  }

  public ValuationMeetingResponse getById(UUID id) {
    ValuationMeeting valuationMeeting =
        valuationMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    return valuationMeetingMapper.toResponse(valuationMeeting);
  }

  public PageResponse<ValuationMeetingResponse> getAll(
      Specification<ValuationMeeting> spec, Pageable page) {
    var result =
        valuationMeetingRepository.findAll(spec, page).map(valuationMeetingMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

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

  public ValuationMeetingResponse getByCreditId(UUID creditId) {
    var valuationMeeting =
        valuationMeetingRepository
            .findByApplicationId(creditId)
            .orElseThrow(() -> new NotFoundException("ValuationMeeting not found"));
    return valuationMeetingMapper.toResponse(valuationMeeting);
  }
}
