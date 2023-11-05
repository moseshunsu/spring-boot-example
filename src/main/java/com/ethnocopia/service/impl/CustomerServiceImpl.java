package com.ethnocopia.service.impl;

import com.ethnocopia.dto.CustomerRequest;
import com.ethnocopia.dto.Response;
import com.ethnocopia.entity.Customer;
import com.ethnocopia.repository.CustomerRepository;
import com.ethnocopia.service.CustomerService;
import com.ethnocopia.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customerList = customerRepository.findAll()
                .stream()
                .toList();

        return !customerList.isEmpty() ? ResponseEntity.ok(customerList) : ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Response> addCustomer(CustomerRequest customerRequest) {
        boolean isMailExists = customerRepository.findAll()
                .stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(customerRequest.getEmail()));

        if (isMailExists) {
            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtils.MAIL_EXISTS_CODE)
                    .responseMessage(ResponseUtils.MAIL_EXISTS_MESSAGE)
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }

        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .age(customerRequest.getAge())
                .build();
        customerRepository.save(customer);

        return new ResponseEntity<>(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.REGISTRATION_SUCCESS_MESSAGE)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<Response> deleteCustomer(Long id) {
        boolean isIdExists = customerRepository.existsById(id);

        if (!isIdExists) {
            return customerIdNotFound();
        }

        customerRepository.deleteById(id);
        return new ResponseEntity<>(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.CUSTOMER_DELETE_MESSAGE)
                        .build(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Response> updateCustomer(Long id, CustomerRequest request) {
        Optional<Customer> customerWrapper = customerRepository.findById(id);

        if (customerWrapper.isEmpty()) {
            customerIdNotFound();
        }

        Customer customer = customerWrapper.get();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAge(request.getAge());
        customerRepository.save(customer);

        return new ResponseEntity<>(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.CUSTOMER_DETAILS_SUCCESS_MESSAGE)
                        .build(),
                HttpStatus.OK
        );
    }

    private ResponseEntity<Response> customerIdNotFound () {
        return new ResponseEntity<>(
                Response.builder()
                        .responseCode(ResponseUtils.ID_NOT_FOUND_CODE)
                        .responseMessage(ResponseUtils.ID_NOT_FOUND_MESSAGE)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
