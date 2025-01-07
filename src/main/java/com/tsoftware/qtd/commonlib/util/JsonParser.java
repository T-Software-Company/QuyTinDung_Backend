package com.tsoftware.qtd.commonlib.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class JsonParser {
  private static final JsonMapper mapper =
      JsonMapper.builder()
          .addModule(new ParameterNamesModule())
          .addModule(new Jdk8Module())
          .addModule(new JavaTimeModule())
          .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .build();

  public static <T> T getValueByPath(Object obj, String path, Class<T> tClass) {
    return getValueByPath(mapper, obj, path, tClass);
  }

  public static <T> T getValueByPath(
      ObjectMapper objectMapper, Object obj, String path, Class<T> tClass) {
    try {
      // Convert the object to a JsonNode
      JsonNode rootNode = objectMapper.valueToTree(obj);

      // Split the path into parts
      String[] pathParts = path.split("\\.");

      JsonNode currentNode = rootNode;
      for (String part : pathParts) {
        if (currentNode == null) {
          return null;
        }

        // Handle array access
        if (part.contains("[") && part.contains("]")) {
          String fieldName = part.substring(0, part.indexOf("["));
          int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));
          currentNode = currentNode.get(fieldName).get(index);
        } else {
          currentNode = currentNode.get(part);
        }
      }

      // Convert the final node back to an Object
      return objectMapper.treeToValue(currentNode, tClass);
    } catch (Exception e) {
      // Handle or log the exception as needed
      log.error("Error while getting value by path: {}", e.getMessage());
      return null;
    }
  }

  public static String toString(final Object obj) {
    return toString(mapper, obj);
  }

  public static String toString(final ObjectMapper objectMapper, final Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (final JsonProcessingException jpe) {
      log.error(jpe.getMessage());
    }
    return null;
  }

  public static <T> T toObject(final String strValue, final Class<T> type) {
    return toObject(mapper, strValue, type);
  }

  public static <T> T toObject(final String strValue, final TypeReference<T> type) {
    return toObject(mapper, strValue, type);
  }

  public static <T> T toObject(
      final ObjectMapper objectMapper, final String strValue, final Class<T> type) {
    try {
      return objectMapper.readValue(strValue, type);
    } catch (final JsonProcessingException jpe) {
      log.error(jpe.getMessage(), jpe);
    }
    return null;
  }

  public static <T> T toObject(
      final ObjectMapper objectMapper, final String strValue, final TypeReference<T> type) {
    try {
      return objectMapper.readValue(strValue, type);
    } catch (final Exception jpe) {
      log.error(jpe.getMessage());
    }
    return null;
  }

  public static <T> T convert(final Object source, final Class<T> type) {
    return convert(mapper, source, type);
  }

  public static <T> T convert(
      final ObjectMapper objectMapper, final Object source, final Class<T> type) {
    return objectMapper.convertValue(source, type);
  }

  public static <T> T convert(final Object source, final TypeReference<T> type) {
    return mapper.convertValue(source, type);
  }

  public static <T> T convert(
      final ObjectMapper objectMapper, final Object source, final TypeReference<T> type) {
    return objectMapper.convertValue(source, type);
  }

  public static Object setValue(final Object object, final String fieldName, final String value) {
    return setValue(mapper, object, fieldName, value);
  }

  public static Object setValue(
      final ObjectMapper objectMapper,
      final Object object,
      final String fieldName,
      final String value) {
    final var jsonNode = objectMapper.valueToTree(object);
    ((ObjectNode) jsonNode).put(fieldName, value);
    try {
      return objectMapper.treeToValue(jsonNode, object.getClass());
    } catch (final Exception e) {
      return null;
    }
  }

  public static Object setValueObject(final Object object, final String fieldName, final Object obj)
      throws Exception {
    return setValueObject(mapper, object, fieldName, obj);
  }

  public static Object setValueObject(
      final ObjectMapper objectMapper,
      final Object object,
      final String fieldName,
      final Object obj)
      throws JsonProcessingException {
    final var jsonNode = objectMapper.valueToTree(object);
    ((ObjectNode) jsonNode).putPOJO(fieldName, obj);
    return objectMapper.treeToValue(jsonNode, object.getClass());
  }
}
