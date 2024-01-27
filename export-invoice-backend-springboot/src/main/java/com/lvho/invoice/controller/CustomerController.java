package com.lvho.invoice.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvho.invoice.data.response.CustomerResponse;
import com.lvho.invoice.entity.Customer;
import com.lvho.invoice.service.CustomerService;
import com.lvho.invoice.utils.Mapper;

import java.util.List;

@RestController
public class CustomerController
{
    @Autowired
    private CustomerService service;

    @Autowired
    private Mapper mapper;

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @GetMapping("/customer")
    public List<CustomerResponse> getAll()
    {
        logger.info("Getting all customers");
        return mapper.convertToCustomerResponse(service.getAll());
    }

    @GetMapping("customer/{id}")
    public CustomerResponse get(@PathVariable String id)
    {
        return mapper.convertToCustomerResponse(service.getById(id));
    }

    @PostMapping("customer")
    public ResponseEntity<Customer> create(@RequestBody Customer customer)
    {
        Customer createdCustomer = service.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable String id) 
    {
        Customer deletedCustomer = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToCustomerResponse(deletedCustomer));
    }

    @PutMapping("/customer")
    public ResponseEntity<CustomerResponse> update(@RequestBody Customer customer) {
        Customer updatedCustomer = service.update(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToCustomerResponse(updatedCustomer));
    }
}
