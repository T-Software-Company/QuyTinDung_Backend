package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.CreditRatingRequest;
import com.tsoftware.qtd.dto.application.CreditResponse;
import com.tsoftware.qtd.event.CreditRatingCreatedEvent;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.CreditRatingMapper;
import com.tsoftware.qtd.repository.ApplicationRepository;
import com.tsoftware.qtd.repository.CreditRatingRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreditRatingService {

  private final CreditRatingRepository creditRatingRepository;
  private final ApplicationRepository applicationRepository;
  private final CreditRatingMapper creditRatingMapper;
  private final ApplicationEventPublisher applicationEventPublisher;

  public CreditResponse create(CreditRatingRequest request, UUID applicationId) {
    var application =
        applicationRepository
            .findById(applicationId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "Application not found: " + applicationId));

    var creditRating = creditRatingMapper.toEntity(request);
    creditRating.setApplication(application);

    var saved = creditRatingRepository.save(creditRating);
    var result = creditRatingMapper.toResponse(saved);

    applicationEventPublisher.publishEvent(new CreditRatingCreatedEvent(this, result));

    return result;
  }

  public CreditResponse update(UUID id, CreditRatingRequest request) {
    var creditRating =
        creditRatingRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "Credit rating not found: " + id));

    creditRatingMapper.updateEntity(request, creditRating);
    return creditRatingMapper.toResponse(creditRatingRepository.save(creditRating));
  }

  public void delete(UUID id) {
    creditRatingRepository.deleteById(id);
  }

  public CreditResponse getById(UUID id) {
    return creditRatingMapper.toResponse(
        creditRatingRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND, "Credit rating not found: " + id)));
  }

  public List<CreditResponse> getAll() {
    return creditRatingRepository.findAll().stream().map(creditRatingMapper::toResponse).toList();
  }

  public CreditResponse getByCreditId(UUID creditId) {
    return creditRatingMapper.toResponse(
        creditRatingRepository
            .findByApplicationId(creditId)
            .orElseThrow(
                () ->
                    new CommonException(
                        ErrorType.ENTITY_NOT_FOUND,
                        "Credit rating not found for credit: " + creditId)));
  }
}
