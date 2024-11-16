package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.LoanRequestRequest;
import com.tsoftware.qtd.dto.credit.LoanRequestResponse;
import com.tsoftware.qtd.entity.LoanRequest;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.LoanRequestMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.repository.LoanRequestRepository;
import com.tsoftware.qtd.service.LoanRequestService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

  @Autowired private LoanRequestRepository loanrequestRepository;

  @Autowired private LoanRequestMapper loanrequestMapper;
  @Autowired private CreditRepository creditRepository;
  @Autowired private GoogleCloudStorageService googleCloudStorageService;
  @Autowired private DocumentService documentService;

  @Override
  public LoanRequestResponse create(LoanRequestRequest loanRequestRequest, Long creditId)
      throws Exception {
    LoanRequest loanrequest = loanrequestMapper.toEntity(loanRequestRequest);
    var credit =
        creditRepository
            .findById(creditId)
            .orElseThrow(() -> new NotFoundException("Credit not found"));
    loanrequest.setCustomer(credit.getCustomer());
    loanrequest.setCredit(credit);
    var templateFile = googleCloudStorageService.downloadFile("tempaltefile");
    var file = documentService.replace(loanrequest, templateFile, 3);
    var url =
        googleCloudStorageService.uploadFile("loan-request/" + UUID.randomUUID() + ".docx", file);
    loanrequest.setDocumentUrl(url);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  @Override
  public LoanRequestResponse update(Long id, LoanRequestRequest loanRequestRequest) {
    LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    loanrequestMapper.updateEntity(loanRequestRequest, loanrequest);
    return loanrequestMapper.toResponse(loanrequestRepository.save(loanrequest));
  }

  @Override
  public void delete(Long id) {
    loanrequestRepository.deleteById(id);
  }

  @Override
  public LoanRequestResponse getById(Long id) {
    LoanRequest loanrequest =
        loanrequestRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("LoanRequest not found"));
    return loanrequestMapper.toResponse(loanrequest);
  }

  @Override
  public List<LoanRequestResponse> getAll() {
    return loanrequestRepository.findAll().stream()
        .map(loanrequestMapper::toResponse)
        .collect(Collectors.toList());
  }
}
