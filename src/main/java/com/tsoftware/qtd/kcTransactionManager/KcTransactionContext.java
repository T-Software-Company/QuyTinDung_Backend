package com.tsoftware.qtd.kcTransactionManager;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KcTransactionContext {
  KcTransactional.KcTransactionType value();
}
