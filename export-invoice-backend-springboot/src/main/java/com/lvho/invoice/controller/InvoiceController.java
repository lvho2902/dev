package com.lvho.invoice.controller;

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

import com.lvho.invoice.data.request.InvoiceCustomerRequest;
import com.lvho.invoice.data.request.InvoiceProjectRequest;
import com.lvho.invoice.data.response.InvoiceResponse;
import com.lvho.invoice.entity.Invoice;
import com.lvho.invoice.service.InvoiceService;
import com.lvho.invoice.utils.Mapper;

import java.util.List;

@RestController
public class InvoiceController
{
    @Autowired
    private InvoiceService service;

    @Autowired
    private Mapper mapper;

    @GetMapping("/invoice")
    public List<InvoiceResponse> getAll()
    {
        return mapper.convertToInvoiceResponse(service.getAll());
    }

    @GetMapping("invoice/{id}")
    public InvoiceResponse get(@PathVariable String id)
    {
        return mapper.convertToInvoiceResponse(service.getById(id));
    }

    @PostMapping("invoice")
    public ResponseEntity<InvoiceResponse> create(@RequestBody Invoice entity)
    {
        Invoice invoice = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToInvoiceResponse(invoice));
    }

    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<InvoiceResponse> delete(@PathVariable String id) 
    {
        Invoice deletedInvoice = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToInvoiceResponse(deletedInvoice));
    }

    @PutMapping("/invoice")
    public ResponseEntity<InvoiceResponse> update(@RequestBody Invoice invoice) {
        Invoice updatedInvoice = service.update(invoice);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToInvoiceResponse(updatedInvoice));
    }

    @PostMapping("/invoice/set-customer")
    public ResponseEntity<InvoiceResponse> setCustomer(@RequestBody InvoiceCustomerRequest request){
        Invoice invoice = service.setCustomer(request.invoiceNumber, request.customerId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToInvoiceResponse(invoice));
    }

    @PostMapping("/invoice/remove-customer")
    public ResponseEntity<InvoiceResponse> removeCustomer(@RequestBody InvoiceCustomerRequest request){
        Invoice invoice = service.removeCustomer(request.invoiceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToInvoiceResponse(invoice));
    }

    @PostMapping("invoice/add-project")
    public ResponseEntity<InvoiceResponse> addProjects(@RequestBody InvoiceProjectRequest request) {
        InvoiceResponse invoiceResponse = mapper.convertToInvoiceResponse(service.addProjects(request.invoiceNumber, request.projectIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceResponse);
    }

    @PostMapping("invoice/remove-project")
    public ResponseEntity<InvoiceResponse> removeProjects(@RequestBody InvoiceProjectRequest request) {
        InvoiceResponse invoiceResponse = mapper.convertToInvoiceResponse(service.removeProjects(request.invoiceNumber, request.projectIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceResponse);
    }
}