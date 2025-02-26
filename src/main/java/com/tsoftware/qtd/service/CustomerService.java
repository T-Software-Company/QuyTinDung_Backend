package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.PageResponse;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.exception.CommonException;
import com.tsoftware.qtd.exception.ErrorType;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CustomerMapper;
import com.tsoftware.qtd.mapper.PageResponseMapper;
import com.tsoftware.qtd.repository.CustomerRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final PageResponseMapper pageResponseMapper;
  private final DocumentService documentService;
  private final KeycloakService keycloakService;

  public CustomerResponse create(CustomerRequest customerRequest) {
    // if customer has been deleted, update it instead of creating a new one
    var existingCustomerOpt =
        customerRepository.findByIdentityInfoIdentifyId(
            customerRequest.getIdentityInfo().getIdentifyId());
    if (existingCustomerOpt.isPresent()) {
      var customer = existingCustomerOpt.get();
      if (customer.getIsDeleted()) {
        return update(customer.getId(), customerRequest);
      } else {
        throw new CommonException(
            ErrorType.DUPLICATED_REQUEST, customerRequest.getIdentityInfo().getIdentifyId());
      }
    }
    var userId = keycloakService.createUser(customerRequest);
    Customer customer = customerMapper.toEntity(customerRequest);
    customer.setIsDeleted(false);
    customer.setEnabled(true);
    customer.setUserId(userId);
    Set<String> urls = getDocumentUrls(customerRequest);
    var entity = customerRepository.save(customer);
    documentService.signCustomerDocument(entity, urls);
    return customerMapper.toResponse(entity);
  }

  private Set<String> getDocumentUrls(CustomerRequest customerRequest) {
    Set<String> urls = new HashSet<>();
    if (!StringUtils.isBlank(customerRequest.getSignaturePhoto())) {
      urls.add(customerRequest.getSignaturePhoto());
    }
    var frontPhotoURL = customerRequest.getIdentityInfo().getFrontPhotoUrl();
    var backPhotoURL = customerRequest.getIdentityInfo().getBackPhotoUrl();
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
    return customerMapper.toResponse(customerRepository.save(customer));
  }

  public void delete(UUID id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    customer.setIsDeleted(true);
    customerRepository.save(customer);
  }

  public CustomerResponse getById(UUID id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    return customerMapper.toResponse(customer);
  }

  public PageResponse<CustomerResponse> getAll(Specification<Customer> spec, Pageable page) {
    try {
      var customerpage = customerRepository.findAll(spec, page).map(customerMapper::toResponse);
      return pageResponseMapper.toPageResponse(customerpage);
    } catch (DataIntegrityViolationException e) {
      throw new CommonException(ErrorType.METHOD_ARGUMENT_NOT_VALID, e.getMessage());
    }
  }

  public void deletes(List<UUID> ids) {
    ids.forEach(this::delete);
  }

  public List<CustomerResponse> getAll() {
    return customerRepository.findAll().stream().map(customerMapper::toResponse).toList();
  }

  public CustomerResponse getProfile() {

    var id = SecurityContextHolder.getContext().getAuthentication().getName();
    return customerMapper.toResponse(
        customerRepository
            .findByUserId(id)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, id)));
  }

  public CustomerResponse getByApplicationId(UUID applicationId) {
    var result =
        customerRepository
            .findByApplicationsId(applicationId)
            .orElseThrow(() -> new CommonException(ErrorType.ENTITY_NOT_FOUND, applicationId));
    return customerMapper.toResponse(result);
  }
}
