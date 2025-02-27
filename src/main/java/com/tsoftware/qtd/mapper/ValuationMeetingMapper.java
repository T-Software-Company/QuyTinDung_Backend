package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.valuation.ValuationMeetingRequest;
import com.tsoftware.qtd.dto.valuation.ValuationMeetingResponse;
import com.tsoftware.qtd.entity.ValuationMeeting;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = {EmployeeMapper.class})
public interface ValuationMeetingMapper {

  ValuationMeeting toEntity(ValuationMeetingRequest dto);

  ValuationMeetingResponse toResponse(ValuationMeeting entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(ValuationMeetingRequest dto, @MappingTarget ValuationMeeting entity);
}
