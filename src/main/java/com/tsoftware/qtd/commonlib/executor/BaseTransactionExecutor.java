package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseTransactionExecutor<T extends AbstractTransaction<?>>
    implements TransactionExecutor<T> {

  @Override
  public Object execute(T transaction) {
    log.info("Starting execution for transaction: {}", transaction.getId());
    try {
      preValidate(transaction);
      var resolvedTransaction = processApproval(transaction);
      Object result = "Approved";
      if (resolvedTransaction.isApproved()) {
        log.info("TransactionDTO already approved: {}", transaction.getId());
        result = doExecute(transaction);
      }
      postExecute(transaction);
      log.info("Completed execution for transaction: {}", transaction.getId());
      return result;
    } catch (Exception e) {
      log.error("Error executing transaction: {}", transaction.getId(), e);
      callBackWhenFall(transaction);
      throw e;
    }
  }

  protected abstract T processApproval(T transaction);

  protected abstract Object doExecute(T transaction);

  protected void preValidate(T transaction) {
    // Default validation logic
  }

  protected abstract void postExecute(T transaction);

  protected void callBackWhenFall(T transaction) {
    // Default pre-execution logic
  }
}
