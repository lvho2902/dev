package com.lvho.invoice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
@RestController
public class EmployeeController
{
    @Autowired
    EmployeeService service;

    @GetMapping("/employee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public List<Employee> getAll()
    {
        return service.getAll();
    }

    @GetMapping("employee/{id}")
    public Employee get(@PathVariable String id)
    {
        return service.getById(id);
    }

    @PostMapping("employee")
    public ResponseEntity<Employee> create(@RequestBody Employee model)
    {
        Employee createdEntity = service.create(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    } 

    @PostMapping("employee/add-list")
    public ResponseEntity<List<Employee>> create(@RequestBody List<Employee> models)
    {
        List<Employee> createdEntities = new ArrayList<Employee>();
        for (Employee model : models) {
            createdEntities.add(service.create(model));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntities);
    } 

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> delete(@PathVariable String id) 
    {
        Employee deletedEntity = service.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(deletedEntity);
    }
    
    @PutMapping("/employee")
    public ResponseEntity<Employee> update(@RequestBody Employee model) {
        Employee updatedEntity = service.update(model.id, model);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedEntity);
    }
}
