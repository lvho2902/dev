// package com.lvho.invoice.controller;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.lvho.invoice.custom.ApiResponse;
// import com.lvho.invoice.entity.PurchaseOrder;
// import com.lvho.invoice.service.PurchaseOrderService;

// import java.util.List;
// @RestController
// public class PurchaseOrderController
// {
//     @Autowired
//     PurchaseOrderService service;

//     @GetMapping("/purchase-order")
//     public List<PurchaseOrder> getAll()
//     {
//         return service.getAll();
//     }

//     @GetMapping("purchase-order/{id}")
//     public PurchaseOrder get(@PathVariable String id)
//     {
//         return service.get(id);
//     }

//     @PostMapping("purchase-order")
//     public ResponseEntity<ApiResponse<PurchaseOrder>> create(@RequestBody PurchaseOrder model)
//     {
//         PurchaseOrder createdEntity = service.create(model);
//         ApiResponse<PurchaseOrder> response = new ApiResponse<PurchaseOrder>("Purchase order entity created successfully", createdEntity);
//         return ResponseEntity.status(HttpStatus.CREATED).body(response);
//     }    

//     @DeleteMapping("/purchase-order/{id}")
//     public ResponseEntity<ApiResponse<PurchaseOrder>> delete(@PathVariable String id) 
//     {
//         PurchaseOrder deletedEntity = service.delete(id);
//         ApiResponse<PurchaseOrder> response = new ApiResponse<PurchaseOrder>("Purchase order entity deleted successfully", deletedEntity);
//         return ResponseEntity.status(HttpStatus.CREATED).body(response);
//     }
    
//     @PutMapping("/purchase-order")
//     public ResponseEntity<ApiResponse<PurchaseOrder>> update(@RequestBody PurchaseOrder model) {
//         PurchaseOrder updatedEntity = service.update(model.id, model);
//         ApiResponse<PurchaseOrder> response = new ApiResponse<PurchaseOrder>("Purchase order entity updated successfully", updatedEntity);
//         return ResponseEntity.status(HttpStatus.CREATED).body(response);
//     }
// }
