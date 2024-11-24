package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.entity.ValuationMeeting;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ValuationMeetingMapper {
  ValuationMeeting toEntity(ValuationMeetingRequest dto);

  ValuationMeetingResponse toResponse(ValuationMeeting entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ValuationMeetingRequest dto, @MappingTarget ValuationMeeting entity);
}
