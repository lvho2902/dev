package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.entity.PurchaseOrder;
import com.lvho.invoice.repository.PurchaseOrderRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService 
{
    @Autowired
    private PurchaseOrderRepo repo;

    public List<PurchaseOrder> getAll()
    {
        return repo.findAll();
    }
    
    public PurchaseOrder get(String id)
    {
        Optional<PurchaseOrder> optionalEntity = repo.findById(id);
        return optionalEntity.orElse(null);
    }
    public PurchaseOrder create(PurchaseOrder model)
    {
        return repo.save(model);
    }

    public PurchaseOrder delete(String id)
    {
        PurchaseOrder model = get(id);
        if(model != null)
        {
            repo.delete(model);
        }
        return model;
    }

    public PurchaseOrder update(String id, PurchaseOrder model) 
    {
        Optional<PurchaseOrder> optionalEntity = repo.findById(id);
        if (optionalEntity.isPresent()) 
        {
            PurchaseOrder existingEntity = optionalEntity.get();
            existingEntity.name = model.name;
            existingEntity.email = model.email;
            existingEntity.phone = model.phone;
            existingEntity.amount = model.amount;
            existingEntity.startDate = model.startDate;
            existingEntity.dueDate = model.dueDate;
            return repo.save(existingEntity);
        } 
        return null;
    }
}
