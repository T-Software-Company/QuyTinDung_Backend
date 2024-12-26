package com.tsoftware.qtd.kcTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class KcAspect {

  private final KcGroupTransactionManager kcGroupTransactionManager;
  private final KcUserTransactionManager kcUserTransactionManager;

  @Before(value = "@annotation(kcTransactionContext)", argNames = "joinPoint,kcTransactionContext")
  public void setContextRequest(JoinPoint joinPoint, KcTransactionContext kcTransactionContext) {
    if (joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) return;
    // group
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.UPDATE_GROUP) {
      String id = (String) joinPoint.getArgs()[1];
      kcGroupTransactionManager.handleUpdate(id);
      return;
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.DELETE_GROUP) {
      kcGroupTransactionManager.handleDelete((String) joinPoint.getArgs()[0]);
    }

    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.ADD_ROLE_TO_GROUP) {
      kcGroupTransactionManager.handleAddRole((String) joinPoint.getArgs()[0]);
      return;
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.REMOVE_ROLE_ON_GROUP) {
      kcGroupTransactionManager.handleRemoveRole((String) joinPoint.getArgs()[0]);
      return;
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.ADD_USER_TO_GROUP) {
      kcGroupTransactionManager.handleAddUser(
          (String) joinPoint.getArgs()[0], (String) joinPoint.getArgs()[1]);
      return;
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.REMOVE_USER_ON_GROUP) {
      kcGroupTransactionManager.handleRemoveUser(
          (String) joinPoint.getArgs()[0], (String) joinPoint.getArgs()[1]);
      return;
    }

    // user
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.UPDATE_USER) {
      kcUserTransactionManager.handleUpdate((String) joinPoint.getArgs()[1]);
      return;
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.ADD_ROLE_TO_USER) {
      kcUserTransactionManager.handleAddRole((String) joinPoint.getArgs()[0]);
      return;
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.REMOVE_ROLE_ON_USER) {
      kcUserTransactionManager.handleRemoveRole((String) joinPoint.getArgs()[0]);
    }
  }

  @AfterReturning(value = "@annotation(kcTransactionContext)", returning = "response")
  public void setContextResponse(KcTransactionContext kcTransactionContext, String response) {
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.CREATE_GROUP) {
      kcGroupTransactionManager.handleCreate(response);
    }
    if (kcTransactionContext.value() == KcTransactional.KcTransactionType.CREATE_USER) {
      kcUserTransactionManager.handleCreate(response);
    }
  }

  @AfterThrowing(value = "@annotation(kcTransactional)")
  public void processKcTransactional(KcTransactional kcTransactional) {

    if (kcTransactional.value() == KcTransactional.KcTransactionType.CREATE_GROUP) {
      kcGroupTransactionManager.handleCreateRollback();
      return;
    }
    if (kcTransactional.value() == KcTransactional.KcTransactionType.UPDATE_GROUP) {
      kcGroupTransactionManager.handleUpdateRollback();
      return;
    }
    if (kcTransactional.value() == KcTransactional.KcTransactionType.DELETE_GROUP) {
      kcGroupTransactionManager.handleDeleteRollback();
      return;
    }

    if (kcTransactional.value() == KcTransactional.KcTransactionType.ADD_ROLE_TO_GROUP) {
      kcGroupTransactionManager.handleAddRoleRollback();
      ;
    }

    if (kcTransactional.value() == KcTransactional.KcTransactionType.REMOVE_ROLE_ON_GROUP) {
      kcGroupTransactionManager.handleRemoveRoleRollback();
      return;
    }
    if (kcTransactional.value() == KcTransactional.KcTransactionType.ADD_USER_TO_GROUP) {
      kcGroupTransactionManager.handleAddUserRollback();
      return;
    }
    if (kcTransactional.value() == KcTransactional.KcTransactionType.REMOVE_USER_ON_GROUP) {
      kcGroupTransactionManager.handleRemoveUserRollback();
      return;
    }

    if (kcTransactional.value() == KcTransactional.KcTransactionType.CREATE_USER) {
      kcUserTransactionManager.handleCreateRollback();
      return;
    }
    if (kcTransactional.value() == KcTransactional.KcTransactionType.UPDATE_USER) {
      kcUserTransactionManager.handleUpdateRollback();
      return;
    }

    if (kcTransactional.value() == KcTransactional.KcTransactionType.ADD_ROLE_TO_USER) {
      kcUserTransactionManager.handleAddRoleRollback();
      return;
    }
    if (kcTransactional.value() == KcTransactional.KcTransactionType.REMOVE_ROLE_ON_USER) {
      kcUserTransactionManager.handleRemoveRoleRollback();
    }
  }

  @After(value = "@annotation(kcTransactional)")
  public void postProcess(KcTransactional kcTransactional) {
    KcGroupTransactionManager.clear();
    KcUserTransactionManager.clear();
  }
}
