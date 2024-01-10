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

import com.lvho.invoice.data.request.PurchaseOrderProjectRequest;
import com.lvho.invoice.data.response.PurchaseOrderResponse;
import com.lvho.invoice.entity.PurchaseOrder;
import com.lvho.invoice.service.PurchaseOrderService;
import com.lvho.invoice.utils.Mapper;

import java.util.List;
@RestController
public class PurchaseOrderController
{
    @Autowired
    private PurchaseOrderService service;

    @Autowired
    private Mapper mapper;

    @GetMapping("/purchase-order")
    public List<PurchaseOrderResponse> getAll()
    {
        return mapper.convertToPurchaseOrderResponse(service.getAll());
    }

    @GetMapping("purchase-order/{id}")
    public PurchaseOrder get(@PathVariable String id)
    {
        return service.getById(id);
    }

    @PostMapping("purchase-order")
    public ResponseEntity<PurchaseOrder> create(@RequestBody PurchaseOrder purchaseOrder)
    {
        PurchaseOrder createdPurchaseOrder = service.create(purchaseOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPurchaseOrder);
    }

    @DeleteMapping("purchase-order/{id}")
    public ResponseEntity<PurchaseOrderResponse> delete(@PathVariable String id) 
    {
        PurchaseOrder deletedPurchaseOrder = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToPurchaseOrderResponse(deletedPurchaseOrder));
    }

    @PutMapping("/purchase-order")
    public ResponseEntity<PurchaseOrderResponse> update(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder updatedPurchaseOrder = service.update(purchaseOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToPurchaseOrderResponse(updatedPurchaseOrder));
    }

    @PostMapping("purchase-order/add-project")
    public ResponseEntity<PurchaseOrderResponse> addProjects(@RequestBody PurchaseOrderProjectRequest entity) {
        PurchaseOrderResponse purchaseOrderResponse = mapper.convertToPurchaseOrderResponse(service.addProjects(entity.purchaseOrderId, entity.projectIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrderResponse);
    }

    @PostMapping("purchase-order/remove-project")
    public ResponseEntity<PurchaseOrderResponse> removeProjects(@RequestBody PurchaseOrderProjectRequest entity) {
        PurchaseOrderResponse purchaseOrderResponse = mapper.convertToPurchaseOrderResponse(service.removeProjects(entity.purchaseOrderId, entity.projectIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrderResponse);
    }
}
