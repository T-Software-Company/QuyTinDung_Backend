package com.tsoftware.qtd.service;

import com.tsoftware.qtd.dto.application.*;
import com.tsoftware.qtd.entity.Application;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ApplicationService {
  ApplicationResponse create(UUID customerId) throws Exception;

  ApplicationResponse update(UUID id, ApplicationDTO applicationRequest);

  void delete(UUID id);

  ApplicationDTO getById(UUID id);

  Page<ApplicationResponse> getAll(Specification<Application> spec, Pageable page);
}
