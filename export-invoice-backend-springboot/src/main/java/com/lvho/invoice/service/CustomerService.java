package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.exception.BadRequestException;
import com.lvho.invoice.entity.Customer;
import com.lvho.invoice.repository.CustomerRepository;
import com.lvho.invoice.utils.Constants;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService 
{
    @Autowired
    private CustomerRepository customerRepo;

    public List<Customer> getAll()
    {
        return customerRepo.findAll();
    }
    
    public Customer getById(String id)
    {
        Optional<Customer> optionalEntity = customerRepo.findById(id);
        return optionalEntity.orElse(null);
    }
    public Customer create(Customer customer)
    {
        if(customer.getName() == null || customer.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(customer.getEmail() == null || customer.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(customer.getPhone() == null || customer.getPhone().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(customer.getAddress() == null || customer.getAddress().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);
        if(customer.getAmount() < 0) throw new BadRequestException(Constants.MESSAGE_INVALID_AMOUNT);
        if(customerRepo.findByName(customer.getName()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_CUSTOMER_NAME_EXIST);
        if(customerRepo.findByEmail(customer.getEmail()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_CUSTOMER_EMAIL_EXIST);

        return customerRepo.save(customer);
    }

    public Customer delete(String id)
    {
        Customer customer = getById(id);
        if(customer == null) throw new BadRequestException(Constants.MESSAGE_CUSTOMER_ID_NOT_EXIST);
        customerRepo.deleteById(id);
        return customer;
    }

    public Customer update(Customer model){
        Customer customer = getById(model.getId());
        if(customer == null) throw new BadRequestException(Constants.MESSAGE_CUSTOMER_ID_NOT_EXIST);
        
        if(model.getName() == null || model.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(model.getEmail() == null || model.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(customer.getPhone() == null || customer.getPhone().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_PHONE);
        if(customer.getAddress() == null || customer.getAddress().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_ADDRESS);
        if(model.getAmount() < 0) throw new BadRequestException(Constants.MESSAGE_INVALID_AMOUNT);

        if(!customer.getName().equals(model.getName()) && customerRepo.findByName(model.getName()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_CUSTOMER_NAME_EXIST);
        if(!customer.getEmail().equals(model.getEmail()) &&customerRepo.findByEmail(customer.getEmail()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_CUSTOMER_EMAIL_EXIST);
        
        customer.setName(model.getName());
        customer.setEmail(model.getEmail());
        customer.setPhone(model.getPhone());
        customer.setAmount(model.getAmount());
        customer.setAddress(model.getAddress());
        return customerRepo.save(customer);
    }
}
