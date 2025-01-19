package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;

public interface TransactionExecutor<T extends AbstractTransaction<?>> {
  T execute(T transaction, ActionStatus status);
}
