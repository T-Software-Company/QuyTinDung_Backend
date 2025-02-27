package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingRequest;
import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingResponse;
import com.tsoftware.qtd.entity.AppraisalMeeting;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface AppraisalMeetingMapper {
  AppraisalMeeting toEntity(AppraisalMeetingRequest request);

  AppraisalMeetingResponse toResponse(AppraisalMeeting entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(AppraisalMeetingRequest request, @MappingTarget AppraisalMeeting entity);
}
