package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.exception.BadRequestException;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.entity.PurchaseOrder;
import com.lvho.invoice.repository.ProjectRepository;
import com.lvho.invoice.repository.PurchaseOrderRepository;
import com.lvho.invoice.utils.Constants;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService 
{
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    @Autowired
    private ProjectRepository projectRepo;

    public List<PurchaseOrder> getAll()
    {
        return purchaseOrderRepo.findAll();
    }
    
    public PurchaseOrder getById(String id)
    {
        Optional<PurchaseOrder> optionalEntity = purchaseOrderRepo.findById(id);
        return optionalEntity.orElse(null);
    }
    public PurchaseOrder create(PurchaseOrder purchaseOrder)
    {
        if(purchaseOrder.getName() == null || purchaseOrder.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(purchaseOrder.getEmail() == null || purchaseOrder.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        if(purchaseOrder.getStartDate() == null || purchaseOrder.getStartDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(purchaseOrder.getDueDate() == null || purchaseOrder.getDueDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);
        if(purchaseOrderRepo.findByName(purchaseOrder.getName()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_PURCHASE_ORDER_NAME_EXIST);

        return purchaseOrderRepo.save(purchaseOrder);
    }

    public PurchaseOrder delete(String id)
    {
        PurchaseOrder purchaseOrder = getById(id);
        if(purchaseOrder == null) throw new BadRequestException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST);
        purchaseOrder.removeThisInAllProject();
        purchaseOrderRepo.deleteById(id);
        return purchaseOrder;
    }

    public PurchaseOrder update(PurchaseOrder model){
        PurchaseOrder purchaseOrder = getById(model.getId());
        if(purchaseOrder == null) throw new BadRequestException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST);
        
        if(model.getName() == null || model.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(!purchaseOrder.getName().equals(model.getName()) && purchaseOrderRepo.findByName(model.getName()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_PURCHASE_ORDER_NAME_EXIST);
         if(model.getEmail() == null || model.getEmail().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_EMAIL);
        
        if(model.getAmount() < 0) throw new BadRequestException(Constants.MESSAGE_INVALID_AMOUNT);
        if(model.getStartDate() == null || model.getStartDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(model.getDueDate() == null || model.getDueDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);

        purchaseOrder.setName(model.getName());
        purchaseOrder.setEmail(model.getEmail());
        purchaseOrder.setPhone(model.getPhone());
        purchaseOrder.setAmount(model.getAmount());
        purchaseOrder.setStartDate(model.getStartDate());
        purchaseOrder.setDueDate(model.getDueDate());
        return purchaseOrderRepo.save(purchaseOrder);
    }

    public PurchaseOrder addProjects(String purchaseOrderId, List<String> projectIds){
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(purchaseOrderId).orElse(null);
        if(purchaseOrder == null) throw new BadRequestException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST);

        projectIds.forEach(projectId ->{
            Project project = projectRepo.findById(projectId).orElse(null);
            if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);
            
            purchaseOrder.addProject(project);
        });

        return purchaseOrderRepo.save(purchaseOrder);
    }
    
    public PurchaseOrder removeProjects(String purchaseOrderId, List<String> projectIds){
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(purchaseOrderId).orElse(null);
        if(purchaseOrder == null) throw new BadRequestException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST);

        projectIds.forEach(projectId ->{
            Project project = projectRepo.findById(projectId).orElse(null);
            if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);
            
            purchaseOrder.removeProject(project);
        });

        return purchaseOrderRepo.save(purchaseOrder);
    }
}
