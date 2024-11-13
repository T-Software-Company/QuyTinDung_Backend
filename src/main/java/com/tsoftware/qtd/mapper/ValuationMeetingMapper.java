package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingDto;
import com.tsoftware.qtd.entity.ValuationMeeting;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ValuationMeetingMapper {
  ValuationMeeting toEntity(ValuationMeetingDto dto);

  ValuationMeetingDto toDto(ValuationMeeting entity);

  void updateEntity(ValuationMeetingDto dto, @MappingTarget ValuationMeeting entity);
}
