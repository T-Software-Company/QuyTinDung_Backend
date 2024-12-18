package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.ApplicationDTO;
import com.tsoftware.qtd.dto.application.ApplicationRequest;
import com.tsoftware.qtd.dto.application.ApplicationResponse;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {
  ApplicationResponse create(ApplicationRequest applicationRequest) throws Exception;

  ApplicationResponse update(UUID id, ApplicationRequest applicationRequest);

  void delete(UUID id);

  ApplicationDTO getById(UUID id);

  List<ApplicationResponse> getAll();
}
