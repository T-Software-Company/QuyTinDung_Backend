package com.tsoftware.qtd.commonlib.executor;

import com.tsoftware.qtd.commonlib.model.AbstractTransaction;

public interface TransactionExecutorRegistry {
  <T extends AbstractTransaction<?>> TransactionExecutor<T> getExecutor(Enum<?> type);
}
