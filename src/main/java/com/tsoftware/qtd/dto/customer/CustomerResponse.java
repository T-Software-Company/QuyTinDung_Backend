package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tsoftware.commonlib.model.AbstractWorkflowResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse extends AbstractWorkflowResponse<CustomerDTO> {}
