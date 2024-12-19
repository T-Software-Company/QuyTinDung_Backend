package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsoftware.commonlib.model.AbstractWorkflowRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest extends AbstractWorkflowRequest<CustomerDTO> {}
