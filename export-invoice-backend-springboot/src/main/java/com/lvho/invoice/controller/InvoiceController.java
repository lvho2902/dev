package com.lvho.invoice.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("invoice/export/{id}")
    public InvoiceResponse export(@PathVariable String id) {

        Invoice invoice = service.getById(id);

        if(invoice != null){
            String path = "d:\\invoice.docx";
            String pathCoppy = "d:\\name_invoice.docx";
            try (XWPFDocument document = new XWPFDocument(java.nio.file.Files.newInputStream(java.nio.file.Paths.get(path)))){

                List<XWPFParagraph> xwpfParagraphList = document.getParagraphs();

                for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
                    for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
                        String docText = xwpfRun.getText(0);
                        if(docText != null){
                            docText = docText.replace("customerName", invoice.getCustomer().getName())
                            .replace("customerPhone", invoice.getCustomer().getPhone())
                            .replace("customerEmail", invoice.getCustomer().getEmail())
                            .replace("customerAddress", invoice.getCustomer().getAddress())
                            .replace("invoiceNumber", invoice.getNumber())
                            .replace("invoiceStartDate", invoice.getStartDate())
                            .replace("invoiceDueDate", invoice.getDueDate());
                            
                            System.out.println(docText);
                            xwpfRun.setText(docText, 0);
                        }
                    }
                }

                try (FileOutputStream out = new FileOutputStream(pathCoppy.replace("name", invoice.getCustomer().getName()))) {
                    document.write(out);
                }

            } catch (IOException e) {
                System.out.println("aaa" + e.getMessage());
            }
        }

        return mapper.convertToInvoiceResponse(invoice);
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
        Invoice invoice = service.setCustomer(request.invoiceId, request.customerId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToInvoiceResponse(invoice));
    }

    @PostMapping("/invoice/remove-customer")
    public ResponseEntity<InvoiceResponse> removeCustomer(@RequestBody InvoiceCustomerRequest request){
        Invoice invoice = service.removeCustomer(request.invoiceId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToInvoiceResponse(invoice));
    }

    @PostMapping("invoice/add-project")
    public ResponseEntity<InvoiceResponse> addProjects(@RequestBody InvoiceProjectRequest request) {
        InvoiceResponse invoiceResponse = mapper.convertToInvoiceResponse(service.addProjects(request.invoiceId, request.projectIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceResponse);
    }

    @PostMapping("invoice/remove-project")
    public ResponseEntity<InvoiceResponse> removeProjects(@RequestBody InvoiceProjectRequest request) {
        InvoiceResponse invoiceResponse = mapper.convertToInvoiceResponse(service.removeProjects(request.invoiceId, request.projectIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceResponse);
    }
}