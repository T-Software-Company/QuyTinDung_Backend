package com.tsoftware.qtd.executor;

import com.tsoftware.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.commonlib.model.Transaction;
import com.tsoftware.qtd.constants.EnumType.TransactionCategory;
import org.springframework.stereotype.Service;

@Service("accountExecutor")
public class AccountExecutor extends BaseTransactionExecutor {

  @Override
  protected Object doExecute(Transaction transaction) {
    System.out.println("Executing Account Transaction");
    return null;
  }

  @Override
  public String getCategory() {
    return TransactionCategory.ACCOUNT.name();
  }
}
