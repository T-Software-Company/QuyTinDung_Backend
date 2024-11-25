package com.tsoftware.qtd.context;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import com.tsoftware.qtd.model.Workflow;
import com.tsoftware.qtd.util.JsonParser;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class WorkflowContext {
  private static final ThreadLocal<Workflow> context = new ThreadLocal<>();

  public static Workflow get() {
    return context.get();
  }

  public static void set(Workflow workflow) {
    context.set(workflow);
  }

  public static void clear() {
    context.remove();
  }

  public static void putMetadata(Object obj) {
    var map = JsonParser.convert(obj, Map.class);
    if (get() == null) return;
    if (get().getMetadata() == null) return;
    get().getMetadata().putAll(map);
  }

  private static void setValueWithCreateMissingNode(
      DocumentContext context, String path, Object value) {
    int pos = path.lastIndexOf('.');
    String parent = (pos == -1) ? "$" : path.substring(0, pos);
    String child = path.substring(pos + 1);
    try {
      context.read(parent); // EX if parent missing
    } catch (PathNotFoundException e) {
      setValueWithCreateMissingNode(
          context, parent, new LinkedHashMap<>()); // (recursively) Create missing parent
    }
    context.put(parent, child, value);
  }

  public static void putMetadata(String key, Object value) {
    if (get() == null) return;
    if (get().getMetadata() == null) return;
    setValueWithCreateMissingNode(JsonPath.parse(get().getMetadata()), key, value);
  }

  public static Object getMetadata(String key) {
    if (get() == null) return null;
    if (get().getMetadata() == null) return null;
    DocumentContext context = JsonPath.parse(get().getMetadata());
    try {
      return context.read(key);
    } catch (PathNotFoundException e) {
      return null;
    }
  }

  public static void setStatus(WorkflowStatus status) {
    if (get() == null) return;
    get().setStatus(status);
  }
}
