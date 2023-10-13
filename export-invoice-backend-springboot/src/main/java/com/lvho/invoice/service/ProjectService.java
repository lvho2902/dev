package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.entity.Project;
import com.lvho.invoice.entity.PurchaseOrder;
import com.lvho.invoice.repository.ProjectRepo;
import com.lvho.invoice.repository.PurchaseOrderRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService 
{
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    public List<Project> getAll()
    {
        return projectRepo.findAll();
    }
    
    public Project get(String id)
    {
        Optional<Project> optionalEntity = projectRepo.findById(id);
        return optionalEntity.orElse(null);
    }

    public Project create(Project model, String purchaseOrderId)
    {
        PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(purchaseOrderId).orElse(null);
        model.purchaseOrder = purchaseOrder;
        return projectRepo.save(model);
    }

    public Project delete(String id)
    {
        Project model = get(id);
        if(model != null)
        {
            projectRepo.delete(model);
        }
        return model;
    }

    public Project update(String id, Project updatedModel) 
    {
        Optional<Project> optionalEntity = projectRepo.findById(id);
        if (optionalEntity.isPresent()) 
        {
            Project existingEntity = optionalEntity.get();
            existingEntity.name = updatedModel.name;
            return projectRepo.save(existingEntity);
        } 
        return null;
    }
}
