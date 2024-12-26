package com.tsoftware.qtd.service.impl;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import com.tsoftware.commonlib.context.WorkflowContext;
import com.tsoftware.commonlib.exception.WorkflowException;
import com.tsoftware.commonlib.model.Workflow;
import com.tsoftware.commonlib.util.JsonParser;
import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.customer.CustomerDTO;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CustomerMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.service.CustomerService;
import com.tsoftware.qtd.service.KeycloakService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final PageResponseMapper pageResponseMapper;
  private final DocumentService documentService;
  private final WorkflowServiceImpl workflowService;
  private final KeycloakService keycloakService;

  @Override
  public CustomerResponse create(CustomerDTO customerDTO) throws Exception {
    // if customer has been deleted, update it instead of creating a new one
    var existingCustomerOpt =
        customerRepository.findByIdentityInfoIdentifyId(
            customerDTO.getIdentityInfo().getIdentifyId());
    if (existingCustomerOpt.isPresent()) {
      var customer = existingCustomerOpt.get();
      if (customer.getIsDeleted()) {
        return update(customer.getId(), customerDTO);
      } else {
        throw new WorkflowException(HttpStatus.CONFLICT.value(), "Customer already exists.");
      }
    }
    var userId = keycloakService.createUser(customerDTO);

    Customer customer = customerMapper.toEntity(customerDTO);
    customer.setIsDeleted(false);
    customer.setEnabled(true);
    customer.setUserId(userId);
    Set<String> urls = getDocumentUrls(customerDTO);
    var entity = customerRepository.save(customer);
    documentService.signCustomerDocument(entity, urls);
    WorkflowContext.putMetadata(customer);
    Workflow workflow = WorkflowContext.get();
    workflow.setTargetId(entity.getId());
    CustomerResponse response = new CustomerResponse();
    response.setData(customerMapper.toDTO(entity));
    return response;
  }

  private Set<String> getDocumentUrls(CustomerDTO customerDTO) {
    Set<String> urls = new HashSet<>();
    if (!StringUtils.isBlank(customerDTO.getSignaturePhoto())) {
      urls.add(customerDTO.getSignaturePhoto());
    }
    var frontPhotoURL =
        JsonParser.getValueByPath(customerDTO, "identityInfo.frontPhotoURL", String.class);
    var backPhotoURL =
        JsonParser.getValueByPath(customerDTO, "identityInfo.backPhotoURL", String.class);
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
  public CustomerResponse update(UUID id, CustomerDTO customerDTO) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id));

    Set<String> urls = getDocumentUrls(customerDTO);
    customer.setIsDeleted(false);
    customerMapper.updateEntity(customerDTO, customer);
    if (CollectionUtils.isNotEmpty(urls)) {
      documentService.signCustomerDocument(customer, urls);
    }
    var result = customerMapper.toDTO(customerRepository.save(customer));
    WorkflowContext.putMetadata(result);
    CustomerResponse response = new CustomerResponse();
    response.setData(result);
    return response;
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
  public CustomerDTO getById(UUID id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    return customerMapper.toDTO(customer);
  }

  @Override
  public PageResponse<CustomerDTO> getAll(Specification<Customer> spec, Pageable page) {
    try {
      var customerpage = customerRepository.findAll(spec, page).map(customerMapper::toDTO);
      return pageResponseMapper.toPageResponse(customerpage);

    } catch (Exception e) {
      throw new CommonException(ErrorType.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
    }
  }

  @Override
  public void deletes(List<UUID> ids) {
    ids.forEach(this::delete);
  }

  @Override
  public List<CustomerDTO> getAll() {
    return customerRepository.findAll().stream().map(customerMapper::toDTO).toList();
  }
}
