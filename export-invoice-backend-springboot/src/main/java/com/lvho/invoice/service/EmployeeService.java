package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.Constants;
import com.lvho.invoice.custom.exception.CustomParameterConstraintException;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.repository.EmployeeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService 
{
    @Autowired
    private EmployeeRepo employeeRepo;

    public List<Employee> getAll()
    {
        return employeeRepo.findAll();
    }
    
    public Employee get(String id)
    {
        Optional<Employee> optionalEntity = employeeRepo.findById(id);
        return optionalEntity.orElse(null);
    }

    public Employee create(Employee model)
    {
        if(model.name == null || model.name.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_NAME, HttpStatus.BAD_REQUEST);
        if(model.email == null || model.email.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        if(employeeRepo.findByEmail(model.email) != null) throw new CustomParameterConstraintException(Constants.MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST, HttpStatus.BAD_REQUEST);
        return employeeRepo.save(model);
    }

    public Employee delete(String id)
    {
        Employee model = get(id);
        if(model == null) throw new CustomParameterConstraintException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        model.projects = null;
        employeeRepo.deleteById(id);
        return model;
    }

    public Employee update(String id, Employee model) 
    {
        Employee employee = get(id);
        if(employee == null) throw new CustomParameterConstraintException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        if(model.name == null || model.name.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_NAME, HttpStatus.BAD_REQUEST);
        if(model.email == null || model.email.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        if(!(employee.email.equals(model.email)) && (employeeRepo.findByEmail(model.email) != null)) throw new CustomParameterConstraintException(Constants.MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST, HttpStatus.BAD_REQUEST);
        employee.name = model.name;
        employee.email = model.email;
        employee.phone = model.phone;
        return employeeRepo.save(employee);
    }
}
