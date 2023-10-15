package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.Constants;
import com.lvho.invoice.custom.exception.CustomParameterConstraintException;
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
        if(model.name == null || model.name.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_NAME, HttpStatus.BAD_REQUEST);
        if(model.email == null || model.email.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        if(model.startDate == null || model.startDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_START_DATE, HttpStatus.BAD_REQUEST);
        if(model.dueDate == null || model.dueDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_DUE_DATE, HttpStatus.BAD_REQUEST);
        if(repo.findByName(model.name) != null) throw new CustomParameterConstraintException(Constants.MESSAGE_SAME_PURCHASE_ORDER_NAME_EXIST, HttpStatus.BAD_REQUEST);
        return repo.save(model);
    }

    public PurchaseOrder delete(String id)
    {
        PurchaseOrder model = get(id);
        if(model == null) throw new CustomParameterConstraintException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        repo.delete(model);
        return model;
    }

    public PurchaseOrder update(String id, PurchaseOrder model) 
    {
        PurchaseOrder purchaseOrder = get(id);
        if(purchaseOrder == null) throw new CustomParameterConstraintException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        if(model.name == null || model.name.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_NAME, HttpStatus.BAD_REQUEST);
        if(model.email == null || model.email.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        if(model.startDate == null || model.startDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_START_DATE, HttpStatus.BAD_REQUEST);
        if(model.dueDate == null || model.dueDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_DUE_DATE, HttpStatus.BAD_REQUEST);
        if(!purchaseOrder.name.equals(model.name) && repo.findByName(model.name) != null) throw new CustomParameterConstraintException(Constants.MESSAGE_SAME_PURCHASE_ORDER_NAME_EXIST, HttpStatus.BAD_REQUEST);
        purchaseOrder.name = model.name;
        purchaseOrder.email = model.email;
        purchaseOrder.phone = model.phone;
        purchaseOrder.amount = model.amount;
        purchaseOrder.startDate = model.startDate;
        purchaseOrder.dueDate = model.dueDate;
        return repo.save(purchaseOrder);
    }
}
