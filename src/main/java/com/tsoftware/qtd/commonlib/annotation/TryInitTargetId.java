package com.tsoftware.qtd.commonlib.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TryInitTargetId {
  String path() default "id";
}
