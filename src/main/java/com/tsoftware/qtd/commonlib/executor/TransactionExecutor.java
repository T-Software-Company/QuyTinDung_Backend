package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;

public interface TransactionExecutor<T extends AbstractTransaction<?>> {
  T execute(T transaction, ApproveStatus status);
}
