package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.entity.AbstractAuditEntity;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

  public InputStream replace(Object object, InputStream templateFile, int depth) throws Exception {
    // Convert object to map for replacement
    Map<String, String> map = objectToMapStructForReplace(object, depth);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // Determine if the file is .doc or .docx
    try (BufferedInputStream bis = new BufferedInputStream(templateFile)) {
      bis.mark(4); // Mark the stream for reset
      byte[] header = new byte[4];
      bis.read(header);
      bis.reset();

      if (isDocFile(header)) {
        // Process .doc file
        try (HWPFDocument doc = new HWPFDocument(bis)) {
          var range = doc.getRange();
          for (Map.Entry<String, String> entry : map.entrySet()) {
            range.replaceText("${{" + entry.getKey() + "}}", entry.getValue());
          }
          doc.write(outputStream);
        }
      } else {
        // Process .docx file
        try (XWPFDocument docx = new XWPFDocument(bis)) {
          for (XWPFParagraph paragraph : docx.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
              String text = run.getText(0);
              if (text != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                  text = text.replace("${{" + entry.getKey() + "}}", entry.getValue());
                }
                run.setText(text, 0);
              }
            }
          }
          docx.write(outputStream);
        }
      }
    }

    return new ByteArrayInputStream(outputStream.toByteArray());
  }

  public boolean isDocFile(byte[] header) {
    return header[0] == (byte) 0xD0
        && header[1] == (byte) 0xCF
        && header[2] == (byte) 0x11
        && header[3] == (byte) 0xE0;
  }

  public static <T> Map<String, String> objectToMapStructForReplace(T object, int depth)
      throws IllegalAccessException {
    Map<String, String> resultMap = new HashMap<>();
    convertObjectToMap(object, "", resultMap, depth, 0);
    return resultMap;
  }

  private static Field[] getAllFields(Object object) {
    if (object == null) {
      return new Field[0];
    }
    Field[] fieldsOfClass = object.getClass().getDeclaredFields();
    Field[] fieldsOfSuperClass = new Field[0];
    Class<?> superClass = object.getClass().getSuperclass();
    if (superClass != null) {
      fieldsOfSuperClass = superClass.getDeclaredFields();
    }
    List<Field> allFields = new ArrayList<>();
    allFields.addAll(Arrays.asList(fieldsOfClass));
    allFields.addAll(Arrays.asList(fieldsOfSuperClass));

    return allFields.toArray(new Field[0]);
  }

  private static <T> void convertObjectToMap(
      T object, String parentKey, Map<String, String> resultMap, int maxDepth, int currentDepth)
      throws IllegalAccessException {
    if (object == null || currentDepth >= maxDepth) {
      return;
    }

    for (Field field : getAllFields(object)) {
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
