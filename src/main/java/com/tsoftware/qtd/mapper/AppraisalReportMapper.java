package com.tsoftware.qtd.mapper;

import com.tsoftware.qtd.dto.appraisal.AppraisalReportRequest;
import com.tsoftware.qtd.dto.appraisal.AppraisalReportResponse;
import com.tsoftware.qtd.entity.AppraisalReport;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = ApprovalMapper.class)
public interface AppraisalReportMapper {
  AppraisalReport toEntity(AppraisalReportRequest dto);

  AppraisalReportResponse toResponse(AppraisalReport entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(AppraisalReportRequest dto, @MappingTarget AppraisalReport entity);
}
