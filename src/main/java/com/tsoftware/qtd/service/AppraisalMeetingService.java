package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingRequest;
import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingResponse;
import com.tsoftware.qtd.entity.AppraisalMeeting;
import com.tsoftware.qtd.entity.Employee;
import com.tsoftware.qtd.event.AppraisalMeetingCreatedEvent;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.AppraisalMeetingMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.AppraisalMeetingRepository;
import com.tsoftware.qtd.repository.EmployeeRepository;
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
public class AppraisalMeetingService {
  private final AppraisalMeetingRepository appraisalMeetingRepository;
  private final ApplicationRepository applicationRepository;
  private final AppraisalMeetingMapper appraisalMeetingMapper;
  private final EmployeeRepository employeeRepository;
  private final PageResponseMapper pageResponseMapper;
  private final ApplicationEventPublisher applicationEventPublisher;

  public AppraisalMeetingResponse create(AppraisalMeetingRequest request, UUID applicationId) {
    AppraisalMeeting appraisalMeeting = appraisalMeetingMapper.toEntity(request);
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "Application not found: " + applicationId));
    appraisalMeeting.setApplication(application);

    var savedEmployee = new ArrayList<Employee>();
    appraisalMeeting
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
    appraisalMeeting.setParticipants(savedEmployee);

    var saved = appraisalMeetingRepository.save(appraisalMeeting);
    var result = appraisalMeetingMapper.toResponse(saved);
    applicationEventPublisher.publishEvent(new AppraisalMeetingCreatedEvent(this, result));
    return result;
  }

  public AppraisalMeetingResponse update(UUID id, AppraisalMeetingRequest request) {
    AppraisalMeeting appraisalMeeting =
        appraisalMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("AppraisalMeeting not found"));
    appraisalMeetingMapper.updateEntity(request, appraisalMeeting);
    return appraisalMeetingMapper.toResponse(appraisalMeetingRepository.save(appraisalMeeting));
  }

  public void delete(UUID id) {
    appraisalMeetingRepository.deleteById(id);
  }

  public AppraisalMeetingResponse getById(UUID id) {
    AppraisalMeeting appraisalMeeting =
        appraisalMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("AppraisalMeeting not found"));
    return appraisalMeetingMapper.toResponse(appraisalMeeting);
  }

  public PageResponse<AppraisalMeetingResponse> getAll(
      Specification<AppraisalMeeting> spec, Pageable page) {
    var result =
        appraisalMeetingRepository.findAll(spec, page).map(appraisalMeetingMapper::toResponse);
    return pageResponseMapper.toPageResponse(result);
  }

  public void addParticipants(UUID id, List<UUID> participantIds) {
    var participants =
        participantIds.stream()
            .map(participantId -> Employee.builder().id(participantId).build())
            .collect(Collectors.toList());
    var appraisalPlan =
        appraisalMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("AppraisalMeeting not found"));
    appraisalPlan.getParticipants().addAll(participants);
    appraisalMeetingRepository.save(appraisalPlan);
  }

  public void removeParticipants(UUID id, List<UUID> participantIds) {
    var appraisalPlan =
        appraisalMeetingRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("AppraisalMeeting not found"));
    var newParticipants =
        appraisalPlan.getParticipants().stream()
            .filter(v -> !participantIds.contains(v.getId()))
            .collect(Collectors.toList());
    appraisalPlan.setParticipants(newParticipants);
    appraisalMeetingRepository.save(appraisalPlan);
  }
}
