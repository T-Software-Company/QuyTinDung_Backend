package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.setting.ApprovalSettingRequest;
import com.tsoftware.qtd.dto.setting.ApprovalSettingResponse;
import com.tsoftware.qtd.entity.ApprovalSetting;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.mapper.SettingMapper;
import com.tsoftware.qtd.repository.ApprovalSettingRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalSettingService {

  private final SettingMapper settingMapper;
  private final ApprovalSettingRepository approvalSettingRepository;
  private final PageResponseMapper pageResponseMapper;

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
    var saved = approvalSettingRepository.save(entity);
    return settingMapper.toResponse(saved);
  }

  public ApprovalSettingResponse update(ApprovalSettingRequest approvalSettingRequest, UUID id) {
    var entity =
        approvalSettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    entity.getRoleApprovalSettings().clear();
    entity.getGroupApprovalSettings().clear();
    settingMapper.updateEntity(entity, approvalSettingRequest);
    entity
        .getGroupApprovalSettings()
        .forEach(groupApprovalSetting -> groupApprovalSetting.setApprovalSetting(entity));
    entity
        .getRoleApprovalSettings()
        .forEach(roleApprovalSetting -> roleApprovalSetting.setApprovalSetting(entity));
    return settingMapper.toResponse(approvalSettingRepository.save(entity));
  }

  public PageResponse<ApprovalSettingResponse> getAll(
      Specification<ApprovalSetting> spec, Pageable pageable) {
    var approvalSettings =
        approvalSettingRepository.findAll(spec, pageable).map(settingMapper::toResponse);
    return pageResponseMapper.toPageResponse(approvalSettings);
  }

  public void delete(UUID id) {
    var entity =
        approvalSettingRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));
    approvalSettingRepository.delete(entity);
  }
}
