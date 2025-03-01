package com.tsoftware.qtd.commonlib.helper;

import com.jayway.jsonpath.JsonPath;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetadataManager {

  public void updateHistoryRequest(Map<String, Object> metadata, Object request, String action) {
    int index = getHistoryIndex(metadata);
    JsonParser.put(metadata, "histories[" + index + "].request", request);
    JsonParser.put(metadata, "histories[" + index + "].action", action);
  }

  public void updateHistoryResponse(Map<String, Object> metadata, Object response) {
    int index = getLastHistoryIndex(metadata);
    JsonParser.put(metadata, "histories[" + index + "].response", response);
  }

  public void updateHistoryError(Map<String, Object> metadata, String error) {
    int index = getLastHistoryIndex(metadata);
    JsonParser.put(metadata, "histories[" + index + "].error", error);
  }

  private int getHistoryIndex(Map<String, Object> metadata) {
    var histories = metadata.get("histories");
    return histories == null ? 0 : JsonPath.parse(histories).read("$.length()", Integer.class);
  }

  private int getLastHistoryIndex(Map<String, Object> metadata) {
    return getHistoryIndex(metadata) - 1;
  }
}
