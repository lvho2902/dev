package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.Constants;
import com.lvho.invoice.custom.exception.BadRequestException;
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
    
    public Employee getById(String id)
    {
        Optional<Employee> optionalEntity = employeeRepo.findById(id);
        return optionalEntity.orElse(null);
    }

    public Employee create(Employee model)
    {
        if(model.name == null || model.name.isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(model.email == null || model.email.isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(employeeRepo.findByEmail(model.email) != null) throw new BadRequestException(Constants.MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST);
        return employeeRepo.save(model);
    }

    public Employee delete(String id)
    {
        Employee model = getById(id);
        if(model == null) throw new BadRequestException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST);
        employeeRepo.deleteById(id);
        return model;
    }

    public Employee update(String id, Employee model) 
    {
        Employee employee = getById(id);
        if(employee == null) throw new BadRequestException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST);
        if(model.name == null || model.name.isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(model.email == null || model.email.isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(!(employee.email.equals(model.email)) && (employeeRepo.findByEmail(model.email) != null)) throw new BadRequestException(Constants.MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST);
        employee.name = model.name;
        employee.email = model.email;
        employee.phone = model.phone;
        return employeeRepo.save(employee);
    }
}
