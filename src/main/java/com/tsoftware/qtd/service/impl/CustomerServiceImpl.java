package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.entity.Customer;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CustomerMapper;
import com.tsoftware.qtd.repository.CustomerRepository;
import com.tsoftware.qtd.service.CustomerService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private CustomerMapper customerMapper;
  @Autowired private GoogleCloudStorageService googleCloudStorageService;

  @Override
  public CustomerResponse create(CustomerRequest customerRequest) throws Exception {
    Customer customer = customerMapper.toEntity(customerRequest);
    var signaturePhotoFile = customerRequest.getSignaturePhotoFile();
    if (signaturePhotoFile != null) {
      var signaturePhotoUrl =
          googleCloudStorageService.uploadFile(
              "signature/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(
                      Objects.requireNonNull(signaturePhotoFile.getOriginalFilename())),
              signaturePhotoFile.getInputStream());
      customer.setSignaturePhoto(signaturePhotoUrl);
    }
    if (customerRequest.getCccd() != null
        && customerRequest.getCccd().getFrontPhotoFile() != null
        && customerRequest.getCccd().getBackPhotoFile() != null) {
      var cccdFrontFile = customerRequest.getCccd().getFrontPhotoFile();
      var cccdBackFile = customerRequest.getCccd().getBackPhotoFile();
      var cccdBackUrl =
          googleCloudStorageService.uploadFile(
              "cccd/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(Objects.requireNonNull(cccdBackFile.getOriginalFilename())),
              cccdBackFile.getInputStream());
      var cccdFrontUrl =
          googleCloudStorageService.uploadFile(
              "cccd/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(Objects.requireNonNull(cccdFrontFile.getOriginalFilename())),
              cccdFrontFile.getInputStream());
      customer.getCccd().setFrontPhotoURL(cccdFrontUrl);
      customer.getCccd().setBackPhotoURL(cccdBackUrl);
    }

    if (customerRequest.getCmnd() != null
        && customerRequest.getCmnd().getFrontPhotoFile() != null
        && customerRequest.getCmnd().getBackPhotoFile() != null) {
      var cmndFrontFile = customerRequest.getCmnd().getFrontPhotoFile();
      var cmndFrontUrl =
          googleCloudStorageService.uploadFile(
              "cmnd/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(Objects.requireNonNull(cmndFrontFile.getOriginalFilename())),
              cmndFrontFile.getInputStream());
      var cmndBackFile = customerRequest.getCmnd().getBackPhotoFile();
      var cmndBackUrl =
          googleCloudStorageService.uploadFile(
              "cmnd/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(Objects.requireNonNull(cmndBackFile.getOriginalFilename())),
              cmndBackFile.getInputStream());
      customer.getCmnd().setFrontPhotoURL(cmndFrontUrl);
      customer.getCmnd().setBackPhotoURL(cmndBackUrl);
    }

    if (customerRequest.getPassPort() != null
        && customerRequest.getPassPort().getFrontPhotoFile() != null
        && customerRequest.getPassPort().getBackPhotoFile() != null) {
      var passPortFrontFile = customerRequest.getPassPort().getFrontPhotoFile();
      var passPortFrontUrl =
          googleCloudStorageService.uploadFile(
              "passPort/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(
                      Objects.requireNonNull(passPortFrontFile.getOriginalFilename())),
              passPortFrontFile.getInputStream());
      var passPortBackFile = customerRequest.getPassPort().getBackPhotoFile();
      var passPortBackUrl =
          googleCloudStorageService.uploadFile(
              "passPort/"
                  + UUID.randomUUID()
                  + "."
                  + getFileExtension(
                      Objects.requireNonNull(passPortBackFile.getOriginalFilename())),
              passPortBackFile.getInputStream());
      customer.getPassPort().setFrontPhotoURL(passPortFrontUrl);
      customer.getPassPort().setBackPhotoURL(passPortBackUrl);
    }

    return customerMapper.toResponse(customerRepository.save(customer));
  }

  @Override
  public CustomerResponse update(Long id, CustomerRequest customerRequest) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    customerMapper.updateEntity(customerRequest, customer);
    return customerMapper.toResponse(customerRepository.save(customer));
  }

  @Override
  public void delete(Long id) {
    customerRepository.deleteById(id);
  }

  @Override
  public CustomerResponse getById(Long id) {
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

  private String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
      return "";
    }
    return fileName.substring(lastIndexOfDot + 1);
  }
}
