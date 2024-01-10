package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.utils.Constants;
import com.lvho.invoice.custom.exception.BadRequestException;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService 
{
    @Autowired
    private EmployeeRepository employeeRepo;

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
        if(model.getName() == null || model.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(model.getEmail() == null || model.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(employeeRepo.findByEmail(model.getEmail()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST);
        return employeeRepo.save(model);
    }

    public Employee delete(String id)
    {
        Employee employee = getById(id);
        if(employee == null) throw new BadRequestException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST);
        employee.removeThisInAllProjects();
        employeeRepo.deleteById(id);
        return employee;
    }

    public Employee update(Employee model) 
    {
        Employee employee = getById(model.getId());
        if(employee == null) throw new BadRequestException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST);
        if(model.getName() == null || model.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(model.getEmail() == null || model.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(!(employee.getEmail().equals(model.getEmail())) && (employeeRepo.findByEmail(model.getEmail()) != null)) throw new BadRequestException(Constants.MESSAGE_SAME_EMPLOYEE_EMAIL_EXIST);
        employee.setName(model.getName());
        employee.setEmail(model.getEmail());
        employee.setPhone(model.getPhone());
        return employeeRepo.save(employee);
    }
}
