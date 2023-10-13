package com.lvho.invoice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvho.invoice.custom.ApiResponse;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.service.EmployeeService;


import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeController
{
    @Autowired
    EmployeeService service;

    @GetMapping("/employee")
    public List<Employee> getAll()
    {
        return service.getAll();
    }

    @GetMapping("employee/{id}")
    public Employee get(@PathVariable String id)
    {
        return service.get(id);
    }

    @PostMapping("employee")
    public ResponseEntity<ApiResponse<Employee>> create(@RequestBody Employee model)
    {
        Employee createdEntity = service.create(model);
        ApiResponse<Employee> response = new ApiResponse<>(true, "Employee entity created successfully", createdEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }    

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<Employee>> delete(@PathVariable String id) 
    {
        Employee deletedEntity = service.delete(id);
        ApiResponse<Employee> response;
        if(deletedEntity != null) response = new ApiResponse<>(true, "Employee entity deleted successfully", deletedEntity);
        else response = new ApiResponse<>(false, "Employee entity create failed", deletedEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/employee")
    public ResponseEntity<ApiResponse<Employee>> update(@RequestBody Employee model) {
        Employee updatedEntity = service.update(model.employeeId, model);
        ApiResponse<Employee> response;
        if(updatedEntity != null) response = new ApiResponse<>(true, "Employee entity udapted successfully", updatedEntity);
        else response = new ApiResponse<>(false, "Employee entity udapted failed", updatedEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
