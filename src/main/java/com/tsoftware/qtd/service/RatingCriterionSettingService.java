package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.setting.RatingCriterionSettingRequest;
import com.tsoftware.qtd.dto.setting.RatingCriterionSettingResponse;
import com.tsoftware.qtd.entity.RatingCriterionSetting;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.mapper.RatingCriterionSettingMapper;
import com.tsoftware.qtd.repository.RatingCriterionSettingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingCriterionSettingService {
  private final RatingCriterionSettingRepository ratingCriterionSettingRepository;
  private final RatingCriterionSettingMapper ratingCriterionSettingMapper;
  private final PageResponseMapper pageResponseMapper;

  public PageResponse<RatingCriterionSettingResponse> findAll(
      Specification<RatingCriterionSetting> spec, Pageable pageable) {
    var pageResult =
        ratingCriterionSettingRepository
            .findAll(spec, pageable)
            .map(ratingCriterionSettingMapper::toResponse);
    return pageResponseMapper.toPageResponse(pageResult);
  }

  public RatingCriterionSettingResponse create(RatingCriterionSettingRequest request) {
    var entity = ratingCriterionSettingMapper.toEntity(request);
    return ratingCriterionSettingMapper.toResponse(ratingCriterionSettingRepository.save(entity));
  }

  public RatingCriterionSettingResponse update(RatingCriterionSettingRequest request, UUID id) {
    var entity =
        ratingCriterionSettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    ratingCriterionSettingMapper.updateEntity(request, entity);
    return ratingCriterionSettingMapper.toResponse(ratingCriterionSettingRepository.save(entity));
  }

  public void delete(UUID id) {
    var entity =
        ratingCriterionSettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    ratingCriterionSettingRepository.delete(entity);
  }

  public boolean hasAnyData() {
    return ratingCriterionSettingRepository.count() > 0;
  }
}
