package com.lvho.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvho.invoice.data.response.InvoiceResponse;
import com.lvho.invoice.data.response.ProjectResponse;
import com.lvho.invoice.entity.Invoice;
import com.lvho.invoice.entity.Project;
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
}