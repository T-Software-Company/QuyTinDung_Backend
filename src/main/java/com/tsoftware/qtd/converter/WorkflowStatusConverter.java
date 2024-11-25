package com.tsoftware.qtd.converter;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class WorkflowStatusConverter implements AttributeConverter<WorkflowStatus, String> {

  @Override
  public String convertToDatabaseColumn(final WorkflowStatus status) {
    if (status == null) {
      return null;
    }
    return status.getShortName();
  }

  @Override
  public WorkflowStatus convertToEntityAttribute(final String json) {
    if (json == null) {
      return null;
    }
    return WorkflowStatus.fromString(json);
  }
}
