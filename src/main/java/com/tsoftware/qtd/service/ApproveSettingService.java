package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.setting.ApproveSettingRequest;
import com.tsoftware.qtd.dto.setting.ApproveSettingResponse;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.SettingMapper;
import com.tsoftware.qtd.repository.ApproveSettingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApproveSettingService {

  private final SettingMapper settingMapper;
  private final ApproveSettingRepository approveSettingRepository;

  public ApproveSettingResponse create(ApproveSettingRequest approveSettingRequest) {
    var entity = settingMapper.toEntity(approveSettingRequest);
    var saved = approveSettingRepository.save(entity);
    return settingMapper.toResponse(saved);
  }

  public ApproveSettingResponse update(ApproveSettingRequest approveSettingRequest, UUID id) {
    var entity =
        approveSettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    settingMapper.updateEntity(entity, approveSettingRequest);
    return settingMapper.toResponse(approveSettingRepository.save(entity));
  }
}
