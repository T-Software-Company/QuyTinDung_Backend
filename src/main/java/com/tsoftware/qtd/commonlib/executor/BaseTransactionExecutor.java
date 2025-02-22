package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseTransactionExecutor<T extends AbstractTransaction<?>>
    implements TransactionExecutor<T> {

  @Override
  public T execute(T transaction, Object... args) {
    log.info("Starting execution for transaction: {}", transaction.getId());
    var type = transaction.getType().name();
    var id = transaction.getId();
    try {
      preValidate(transaction);
      processApproval(transaction, args);
      if (transaction.isApproved()) {
        doExecute(transaction);
        log.info("Transaction already approved: {} - {}", type, id);
      }
      log.info("Completed execution for transaction:{} - {}", type, id);
      return postExecute(transaction);
    } catch (Exception e) {
      log.error("Error executing transaction: {} - {}", type, id, e);
      callBackWhenFall(transaction, e);
      throw e;
    }
  }

  protected abstract void processApproval(T transaction, Object... args);

  protected abstract void doExecute(T transaction);

  protected void preValidate(T transaction) {
    // Default validation logic
  }

  protected abstract T postExecute(T transaction);

  protected void callBackWhenFall(T transaction, Exception e) {
    // Default pre-execution logic
  }
}
