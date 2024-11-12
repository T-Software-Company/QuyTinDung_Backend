package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.CustomerDto;
import com.tsoftware.qtd.response.ApiResponse;
import com.tsoftware.qtd.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired private CustomerService customerService;

  @PostMapping
  public ResponseEntity<ApiResponse<CustomerDto>> create(@RequestBody CustomerDto customerDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", customerService.create(customerDto)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerDto>> update(
      @PathVariable Long id, @RequestBody CustomerDto customerDto) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", customerService.update(id, customerDto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    customerService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerDto>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", customerService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CustomerDto>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", customerService.getAll()));
  }
}
