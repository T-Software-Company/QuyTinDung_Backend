package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseTransactionExecutor<T extends AbstractTransaction<?>>
    implements TransactionExecutor<T> {

  @Override
  public T execute(T transaction) {
    log.info("Starting execution for transaction: {}", transaction.getId());
    try {
      preValidate(transaction);
      var resolvedTransaction = processApproval(transaction);
      if (resolvedTransaction.isApproved()) {
        doExecute(transaction);
        log.info("WorkflowTransactionDTO already approved: {}", transaction.getId());
      }
      log.info("Completed execution for transaction: {}", transaction.getId());
      return postExecute(transaction);
    } catch (Exception e) {
      log.error("Error executing transaction: {}", transaction.getId(), e);
      callBackWhenFall(transaction);
      throw e;
    }
  }

  protected abstract T processApproval(T transaction);

  protected abstract void doExecute(T transaction);

  protected void preValidate(T transaction) {
    // Default validation logic
  }

  protected abstract T postExecute(T transaction);

  protected void callBackWhenFall(T transaction) {
    // Default pre-execution logic
  }
}
