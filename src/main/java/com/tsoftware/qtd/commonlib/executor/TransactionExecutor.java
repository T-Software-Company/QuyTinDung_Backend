package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.model.AbstractTransaction;

public interface TransactionExecutor<T extends AbstractTransaction<?>> {
  Object execute(T transaction);
}
