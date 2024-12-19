package com.tsoftware.qtd.dto;

import com.tsoftware.commonlib.model.AbstractWorkflowRequest;
import com.tsoftware.qtd.dto.transaction.ApproveDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApproveRequest extends AbstractWorkflowRequest<ApproveDTO> {}
