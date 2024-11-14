package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.entity.AbstractAuditEntity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
  @Autowired private GoogleCloudStorageService googleCloudStorageService;

  public String uploadDocument(Object object, String templateUrl, String fileName, int depth)
      throws Exception {

    Map<String, String> map = objectToMapStructForReplace(object, depth);
    throw new Exception("osjdf");

    //		var templateFile = googleCloudStorageService.downloadFile(templateUrl);
    //
    //		WordprocessingMLPackage template = WordprocessingMLPackage.load(templateFile);
    //		MainDocumentPart documentPart = template.getMainDocumentPart();
    //		documentPart.getContent().forEach(element -> {
    //			if (element instanceof Text textElement) {
    //				String text = textElement.getValue();
    //
    //				for (Map.Entry<String, String> entry : map.entrySet()) {
    //					text = text.replace("${{" + entry.getKey() + "}}", entry.getValue());
    //				}
    //				textElement.setValue(text);
    //			}
    //		});
    //
    //		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //		template.save(outputStream);
    //		InputStream updatedDocInputStream = new ByteArrayInputStream(outputStream.toByteArray());

    //		return googleCloudStorageService.uploadFile(fileName.endsWith(".doc") ||
    // fileName.endsWith(".docx") ? fileName : fileName + ".docx", updatedDocInputStream);

  }

  public static <T> Map<String, String> objectToMapStructForReplace(T object, int depth)
      throws IllegalAccessException {
    Map<String, String> resultMap = new HashMap<>();
    convertObjectToMap(object, "", resultMap, depth, 0);
    return resultMap;
  }

  private static <T> void convertObjectToMap(
      T object, String parentKey, Map<String, String> resultMap, int maxDepth, int currentDepth)
      throws IllegalAccessException {
    if (object == null || currentDepth >= maxDepth) {
      return;
    }

    for (Field field : object.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      Object value = field.get(object);

      String key = parentKey.isEmpty() ? field.getName() : parentKey + "." + field.getName();

      if (value instanceof List<?> list) {
        for (int i = 0; i < list.size(); i++) {
          convertObjectToMap(
              list.get(i), key + "[" + i + "]", resultMap, maxDepth, currentDepth + 1);
        }
      } else if (value instanceof AbstractAuditEntity) {
        convertObjectToMap(value, key, resultMap, maxDepth, currentDepth + 1);
      } else if (value != null && !isPrimitiveOrString(value)) {
        convertObjectToMap(value, key, resultMap, maxDepth, currentDepth + 1);
      } else if (value instanceof Enum<?>) {
        try {
          Method getDescriptionMethod = value.getClass().getMethod("getDescription");
          String description = (String) getDescriptionMethod.invoke(value);
          resultMap.put(key, description);
        } catch (Exception e) {
          resultMap.put(key, value.toString());
        }
      } else {
        resultMap.put(key, value != null ? value.toString() : "null");
      }
    }
  }

  private static boolean isPrimitiveOrString(Object value) {
    return value.getClass().isPrimitive()
        || value instanceof String
        || value instanceof Number
        || value instanceof Boolean
        || value.getClass().isEnum();
  }
}
