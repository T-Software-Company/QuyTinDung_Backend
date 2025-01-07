package com.tsoftware.qtd.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tsoftware.qtd.commonlib.model.StepHistory;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Converter(autoApply = true)
public class StepHistoryConverter implements AttributeConverter<List<StepHistory>, String> {

  @Override
  public String convertToDatabaseColumn(List<StepHistory> object) {
    return JsonParser.toString(object);
  }

  @Override
  public List<StepHistory> convertToEntityAttribute(String data) {
    try {
      return JsonParser.toObject(data, new TypeReference<>() {});
    } catch (Exception e) {
      log.error("Failed to convert string to object", e);
      // tracking
      return null;
    }
  }
}
