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

import com.lvho.invoice.data.response.EmployeeResponse;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.service.EmployeeService;
import com.lvho.invoice.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
@RestController
public class EmployeeController
{
    @Autowired
    EmployeeService service;

    @Autowired
    private Mapper mapper;

    @GetMapping("/employee")
    public List<EmployeeResponse> getAll()
    {
        return mapper.convertToEmployeeResponse(service.getAll());
    }

    @GetMapping("employee/{id}")
    public EmployeeResponse get(@PathVariable String id)
    {
        return mapper.convertToEmployeeResponse(service.getById(id));
    }

    @PostMapping("employee")
    public ResponseEntity<EmployeeResponse> create(@RequestBody Employee employee)
    {
        Employee createdEmployee = service.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToEmployeeResponse(createdEmployee));
    } 

    @PostMapping("employee/add-list")
    public ResponseEntity<List<Employee>> create(@RequestBody List<Employee> employees)
    {
        List<Employee> createdEmployees = new ArrayList<Employee>();
        for (Employee employee : employees) {
            createdEmployees.add(service.create(employee));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployees);
    } 

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> delete(@PathVariable String id) 
    {
        Employee deletedEmployee = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedEmployee);
    }
    
    @PutMapping("/employee")
    public ResponseEntity<EmployeeResponse> update(@RequestBody Employee employee) {
        Employee updatedEmployee = service.update(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToEmployeeResponse(updatedEmployee));
    }
}
