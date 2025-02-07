package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.setting.InterestRateSettingRequest;
import com.tsoftware.qtd.dto.setting.InterestRateSettingResponse;
import com.tsoftware.qtd.entity.InterestRateSetting;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.InterestRateSettingMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.InterestRateSettingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestRateSettingService {

  private final InterestRateSettingRepository interestratesettingRepository;
  private final InterestRateSettingMapper interestratesettingMapper;
  private final PageResponseMapper pageResponseMapper;

  public PageResponse<InterestRateSettingResponse> findAll(
      Specification<InterestRateSetting> spec, Pageable pageable) {
    var pageResult =
        interestratesettingRepository
            .findAll(spec, pageable)
            .map(interestratesettingMapper::toResponse);
    return pageResponseMapper.toPageResponse(pageResult);
  }

  public InterestRateSettingResponse create(InterestRateSettingRequest request) {
    var entity = interestratesettingMapper.toEntity(request);
    return interestratesettingMapper.toResponse(interestratesettingRepository.save(entity));
  }

  public InterestRateSettingResponse update(InterestRateSettingRequest request, UUID id) {
    var entity =
        interestratesettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    interestratesettingMapper.updateEntity(request, entity);
    return interestratesettingMapper.toResponse(interestratesettingRepository.save(entity));
  }

  public void delete(UUID id) {
    var entity =
        interestratesettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    interestratesettingRepository.delete(entity);
  }
}
