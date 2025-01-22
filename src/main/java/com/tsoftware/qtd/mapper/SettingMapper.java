package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.setting.ApprovalSettingRequest;
import com.tsoftware.qtd.dto.setting.ApprovalSettingResponse;
import com.tsoftware.qtd.entity.ApprovalSetting;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS)
public interface SettingMapper {

  ApprovalSetting toEntity(ApprovalSettingRequest approvalSettingRequest);

  ApprovalSettingResponse toResponse(ApprovalSetting saved);

  void updateEntity(
      @MappingTarget ApprovalSetting entity, ApprovalSettingRequest approvalSettingRequest);
}
