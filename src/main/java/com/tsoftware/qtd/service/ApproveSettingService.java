package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.setting.ApprovalSettingRequest;
import com.tsoftware.qtd.dto.setting.ApprovalSettingResponse;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.SettingMapper;
import com.tsoftware.qtd.repository.ApproveSettingRepository;
import java.util.Optional;
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

  public ApprovalSettingResponse create(ApprovalSettingRequest approvalSettingRequest) {
    var entity = settingMapper.toEntity(approvalSettingRequest);
    Optional.ofNullable(entity.getGroupApprovalSettings())
        .ifPresent(
            groupApprovalSettings ->
                groupApprovalSettings.forEach(
                    groupApproveSetting -> groupApproveSetting.setApprovalSetting(entity)));
    Optional.ofNullable(entity.getRoleApprovalSettings())
        .ifPresent(
            roleApprovalSettings ->
                roleApprovalSettings.forEach(
                    roleApproveSetting -> roleApproveSetting.setApprovalSetting(entity)));
    var saved = approveSettingRepository.save(entity);
    return settingMapper.toResponse(saved);
  }

  public ApprovalSettingResponse update(ApprovalSettingRequest approvalSettingRequest, UUID id) {
    var entity =
        approveSettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    settingMapper.updateEntity(entity, approvalSettingRequest);
    return settingMapper.toResponse(approveSettingRepository.save(entity));
  }
}
