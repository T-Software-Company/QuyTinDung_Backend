package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.AssetRepissessionNoticeDto;
import com.tsoftware.qtd.entity.AssetRepissessionNotice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssetRepissessionNoticeMapper {
  AssetRepissessionNotice toEntity(AssetRepissessionNoticeDto dto);

  AssetRepissessionNoticeDto toDto(AssetRepissessionNotice entity);

  void updateEntity(AssetRepissessionNoticeDto dto, @MappingTarget AssetRepissessionNotice entity);
}
