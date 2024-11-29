package com.tsoftware.qtd.executor;

import com.tsoftware.commonlib.executor.BaseTransactionExecutor;
import com.tsoftware.commonlib.model.Transaction;
import com.tsoftware.commonlib.util.JsonParser;
import com.tsoftware.qtd.constants.EnumType.TransactionCategory;
import com.tsoftware.qtd.dto.request.CreateCustomerRequest;
import org.springframework.stereotype.Service;

@Service("customerExecutor")
public class CustomerExecutor extends BaseTransactionExecutor {

  @Override
  protected Object doExecute(Transaction transaction) {
    System.out.println("Executing Customer Transaction");
    CreateCustomerRequest request =
        JsonParser.convert(transaction.getMetadata(), CreateCustomerRequest.class);
    return request;
  }

  @Override
  public String getCategory() {
    return TransactionCategory.CUSTOMER.name();
  }
}
