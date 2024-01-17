package com.lvho.invoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.exception.BadRequestException;
import com.lvho.invoice.entity.Customer;
import com.lvho.invoice.entity.Invoice;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.repository.CustomerRepository;
import com.lvho.invoice.repository.InvoiceRepository;
import com.lvho.invoice.repository.ProjectRepository;
import com.lvho.invoice.utils.Constants;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired ProjectRepository projectRepo;

    public List<Invoice> getAll()
    {
        return invoiceRepo.findAll();
    }
    public Invoice getById(String id)
    {
        return invoiceRepo.findById(id).orElse(null);
    }

    public Invoice create(Invoice invoice)
    {
        if(invoice.getStartDate() == null || invoice.getStartDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(invoice.getDueDate() == null || invoice.getDueDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);

        invoice.setTotal(0);
        invoice.generateRandomInvoiceNumber(10);
        
        return invoiceRepo.save(invoice);
    }

    public Invoice delete(String id)
    {
        Invoice invoice = getById(id);
        if(invoice == null) throw new BadRequestException(Constants.MESSAGE_INVOICE_ID_NOT_EXIST);
        invoiceRepo.deleteById(id);

        return invoice;
    }

    public Invoice update(Invoice model){
        Invoice invoice = getById(model.getId());
        if(invoice == null) throw new BadRequestException(Constants.MESSAGE_INVOICE_ID_NOT_EXIST);
        
        if(model.getStartDate() == null || model.getStartDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(model.getDueDate() == null || model.getDueDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);

        invoice.setStartDate(model.getStartDate());
        invoice.setDueDate(model.getDueDate());

        return invoiceRepo.save(invoice);
    }

    public Invoice setCustomer(String invoiceId, String customerId){
        Invoice invoice = invoiceRepo.findById(invoiceId).orElse(null);
        if(invoice == null) throw new BadRequestException(Constants.MESSAGE_INVOICE_ID_NOT_EXIST);

        Customer customer = customerRepo.findById(customerId).orElse(null);
        if(customer == null) throw new BadRequestException(Constants.MESSAGE_CUSTOMER_ID_NOT_EXIST);
        
        invoice.setCustomer(customer);

        return invoiceRepo.save(invoice);
    }

    public Invoice removeCustomer(String invoiceId){
        Invoice invoice = invoiceRepo.findById(invoiceId).orElse(null);
        if(invoice == null) throw new BadRequestException(Constants.MESSAGE_INVOICE_ID_NOT_EXIST);

        invoice.setCustomer(null);

        return invoiceRepo.save(invoice);
    }

     public Invoice addProjects(String invoiceId, List<String> projectIds){
        Invoice invoice = invoiceRepo.findById(invoiceId).orElse(null);
        if(invoice == null) throw new BadRequestException(Constants.MESSAGE_INVOICE_ID_NOT_EXIST);

        projectIds.forEach(projectId ->{
            Project project = projectRepo.findById(projectId).orElse(null);
            if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);
            
            invoice.addProject(project);
        });

        return invoiceRepo.save(invoice);
    }
    
    public Invoice removeProjects(String invoiceId, List<String> projectIds){
        Invoice invoice = invoiceRepo.findById(invoiceId).orElse(null);
        if(invoice == null) throw new BadRequestException(Constants.MESSAGE_INVOICE_ID_NOT_EXIST);

        projectIds.forEach(projectId ->{
            Project project = projectRepo.findById(projectId).orElse(null);
            if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);
            
            invoice.removeProject(project);
        });

        return invoiceRepo.save(invoice);
    }
}
