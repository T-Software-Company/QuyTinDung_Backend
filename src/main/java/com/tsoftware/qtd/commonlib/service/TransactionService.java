package com.tsoftware.qtd.commonlib.service;

import com.tsoftware.qtd.commonlib.annotation.TryTransactionId;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;

public interface TransactionService {
  @TryTransactionId
  <T extends AbstractTransaction<?>> T create(T transaction);
}
