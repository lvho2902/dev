package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.repository.EmployeeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService 
{
    @Autowired
    private EmployeeRepo repo;

    public List<Employee> getAll()
    {
        return repo.findAll();
    }
    
    public Employee get(String id)
    {
        Optional<Employee> optionalEntity = repo.findById(id);
        return optionalEntity.orElse(null);
    }

    public Employee create(Employee model)
    {
        return repo.save(model);
    }

    public Employee delete(String id)
    {
        Employee model = get(id);
        if(model != null)
        {
            repo.delete(model);
        }
        return model;
    }

    public Employee update(String id, Employee model) 
    {
        Optional<Employee> optionalEntity = repo.findById(id);
        if (optionalEntity.isPresent()) 
        {
            Employee existingEntity = optionalEntity.get();
            existingEntity.name = model.name;
            existingEntity.email = model.email;
            existingEntity.phone = model.phone;
            return repo.save(existingEntity);
        } 
        return null;
    }
}
