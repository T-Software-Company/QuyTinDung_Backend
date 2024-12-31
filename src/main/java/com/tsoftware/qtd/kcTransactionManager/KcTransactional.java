package com.tsoftware.qtd.kcTransactionManager;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KcTransactional {
  KcTransactionType value();

  enum KcTransactionType {
    CREATE_USER,
    UPDATE_USER,
    ADD_ROLE_TO_USER,
    REMOVE_ROLE_ON_USER,
    ENABLE_USER,
    DISABLE_USER,
    CREATE_GROUP,
    UPDATE_GROUP,
    DELETE_GROUP,
    ADD_ROLE_TO_GROUP,
    REMOVE_ROLE_ON_GROUP,
    ADD_USER_TO_GROUP,
    ADD_USERS_TO_GROUP,
    REMOVE_USER_ON_GROUP,
    REMOVE_USERS_ON_GROUP
  }
}
