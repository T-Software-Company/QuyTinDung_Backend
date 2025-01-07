package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.model.AbstractWorkflowRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveRequest extends AbstractWorkflowRequest<ApproveDTO> {}
