package com.tsoftware.qtd.configuration;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.commonlib.util.JsonParser;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeLConfig {
  public WorkflowStatus extractStatus(Object histories) {
    var data = JsonParser.convert(histories, List.class);
    var history = JsonParser.convert(data.getLast(), Map.class);
    var response = JsonParser.convert(history.get("response"), AbstractTransaction.class);
    if (response.getStatus().equals(ApproveStatus.APPROVED)) return WorkflowStatus.COMPLETED;
    if (response.getStatus().equals(ApproveStatus.REJECTED)) return WorkflowStatus.DENIED;
    return WorkflowStatus.INPROGRESS;
  }
}
