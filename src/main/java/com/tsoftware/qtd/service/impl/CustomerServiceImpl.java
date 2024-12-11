package com.tsoftware.qtd.service.impl;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import com.tsoftware.commonlib.context.WorkflowContext;
import com.tsoftware.commonlib.exception.WorkflowException;
import com.tsoftware.commonlib.model.Workflow;
import com.tsoftware.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CustomerMapper;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.service.CustomerService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  @Autowired private CustomerRepository customerRepository;
  @Autowired private CustomerMapper customerMapper;
  @Autowired private DocumentService documentService;
  @Autowired private WorkflowServiceImpl workflowService;

  @Override
  public CustomerResponse create(CustomerRequest customerRequest) throws Exception {
    // if customer has been deleted, update it instead of creating a new one
    var existingCustomerOpt =
        customerRepository.findByIdentityInfoIdentifyId(
            customerRequest.getIdentityInfo().getIdentifyId());
    if (existingCustomerOpt.isPresent()) {
      var customer = existingCustomerOpt.get();
      if (customer.getIsDeleted()) {
        return update(customer.getId(), customerRequest);
      } else {
        throw new WorkflowException(HttpStatus.CONFLICT.value(), "Customer already exists.");
      }
    }

    Customer customer = customerMapper.toEntity(customerRequest);
    customer.setIsDeleted(false);
    Set<String> urls = getDocumentUrls(customerRequest);
    var entity = customerRepository.save(customer);
    documentService.signCustomerDocument(entity, urls);
    WorkflowContext.putMetadata(customer);
    Workflow workflow = WorkflowContext.get();
    workflow.setTargetId(entity.getId());
    return customerMapper.toResponse(entity);
  }

  private Set<String> getDocumentUrls(CustomerRequest customerRequest) {
    Set<String> urls = new HashSet<>();
    if (!StringUtils.isBlank(customerRequest.getSignaturePhoto())) {
      urls.add(customerRequest.getSignaturePhoto());
    }
    var frontPhotoURL =
        JsonParser.getValueByPath(customerRequest, "identityInfo.frontPhotoURL", String.class);
    var backPhotoURL =
        JsonParser.getValueByPath(customerRequest, "identityInfo.backPhotoURL", String.class);
    if (frontPhotoURL != null && backPhotoURL != null) {
      urls.add(frontPhotoURL);
      urls.add(backPhotoURL);
    }
    var existingDocs = documentService.findDocumentByUrls(urls);
    if (CollectionUtils.isNotEmpty(existingDocs) && existingDocs.size() < urls.size()) {
      throw new CommonException(
          ErrorType.METHOD_ARGUMENT_NOT_VALID, "Some documents are not found");
    }

    urls.removeAll(existingDocs);
    return urls;
  }

  @Override
  public CustomerResponse update(UUID id, CustomerRequest customerRequest) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));

    Set<String> urls = getDocumentUrls(customerRequest);
    customer.setIsDeleted(false);
    customerMapper.updateEntity(customerRequest, customer);
    if (CollectionUtils.isNotEmpty(urls)) {
      documentService.signCustomerDocument(customer, urls);
    }
    var result = customerMapper.toResponse(customerRepository.save(customer));
    WorkflowContext.putMetadata(result);
    return result;
  }

  @Override
  public void delete(UUID id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    customer.setIsDeleted(true);
    customerRepository.save(customer);
    workflowService
        .getByStatus(customer.getId(), WorkflowStatus.INPROGRESS)
        .forEach(
            workflow -> {
              workflow.setWorkflowStatus(WorkflowStatus.EXPIRED);
              workflowService.save(workflow);
            });
  }

  @Override
  public CustomerResponse getById(UUID id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    return customerMapper.toResponse(customer);
  }

  @Override
  public List<CustomerResponse> getAll() {
    return customerRepository.findAll().stream()
        .map(customerMapper::toResponse)
        .collect(Collectors.toList());
  }
}
