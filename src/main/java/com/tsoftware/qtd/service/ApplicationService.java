package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.credit.ApplicationRequest;
import com.tsoftware.qtd.dto.credit.ApplicationResponse;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {
  ApplicationResponse create(ApplicationRequest applicationRequest) throws Exception;

  ApplicationResponse update(UUID id, ApplicationRequest applicationRequest);

  void delete(UUID id);

  ApplicationResponse getById(UUID id);

  List<ApplicationResponse> getAll();
}
