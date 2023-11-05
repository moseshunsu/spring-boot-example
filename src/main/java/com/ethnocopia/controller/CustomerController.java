package com.ethnocopia.controller;

import com.ethnocopia.dto.CustomerRequest;
import com.ethnocopia.dto.Response;
import com.ethnocopia.entity.Customer;
import com.ethnocopia.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    public ResponseEntity<Response> addCustomer(@Validated @RequestBody CustomerRequest request) {
        return customerService.addCustomer(request);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable("customerId") Long id) {
        return customerService.deleteCustomer(id);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Response> updateCustomer(@PathVariable("customerId") Long id,
                                                   @RequestBody @Validated CustomerRequest request) {
        return customerService.updateCustomer(id, request);
    }

}
