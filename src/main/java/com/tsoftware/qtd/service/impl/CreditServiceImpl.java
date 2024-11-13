package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import com.tsoftware.qtd.entity.Credit;
import com.tsoftware.qtd.exception.NotFoundException;
import com.tsoftware.qtd.mapper.CreditMapper;
import com.tsoftware.qtd.repository.CreditRepository;
import com.tsoftware.qtd.service.CreditService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {

  @Autowired private CreditRepository creditRepository;

  @Autowired private CreditMapper creditMapper;

  @Override
  public CreditResponse create(CreditRequest creditRequest) {
    Credit credit = creditMapper.toEntity(creditRequest);
    return creditMapper.toResponse(creditRepository.save(credit));
  }

  @Override
  public CreditResponse update(Long id, CreditRequest creditRequest) {
    Credit credit =
        creditRepository.findById(id).orElseThrow(() -> new NotFoundException("Credit not found"));
    creditMapper.updateEntity(creditRequest, credit);
    return creditMapper.toResponse(creditRepository.save(credit));
  }

  @Override
  public void delete(Long id) {
    creditRepository.deleteById(id);
  }

  @Override
  public CreditResponse getById(Long id) {
    Credit credit =
        creditRepository.findById(id).orElseThrow(() -> new NotFoundException("Credit not found"));
    return creditMapper.toResponse(credit);
  }

  @Override
  public List<CreditResponse> getAll() {
    return creditRepository.findAll().stream()
        .map(creditMapper::toResponse)
        .collect(Collectors.toList());
  }
}
