package com.ethnocopia.service;

import com.ethnocopia.dto.CustomerRequest;
import com.ethnocopia.dto.Response;
import com.ethnocopia.entity.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<List<Customer>> getCustomers();
    ResponseEntity<Response> addCustomer(CustomerRequest request);
    ResponseEntity<Response> deleteCustomer(Long id);
    ResponseEntity<Response> updateCustomer(Long id, CustomerRequest request);
}
