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

import com.lvho.invoice.custom.ApiResponse;
import com.lvho.invoice.dto.ProjectDto;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.mapper.ProjectMapper;
import com.lvho.invoice.service.ProjectService;
import java.util.List;
@RestController
public class ProjectController
{
    @Autowired
    ProjectService service;

    @GetMapping("/project")
    public List<Project> getAll()
    {
        return service.getAll();
    }

    @GetMapping("project/{id}")
    public Project get(@PathVariable String id)
    {
        return service.get(id);
    }

    @PostMapping("project")
    public ResponseEntity<ApiResponse<Project>> create(@RequestBody ProjectDto dto)
    {
        Project model = ProjectMapper.toProject(dto);
        Project createdEntity = service.create(model, dto.purchaseOrderId);
        ApiResponse<Project> response = new ApiResponse<>(true, "Project entity created successfully", createdEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }    

    @DeleteMapping("/project/{id}")
    public ResponseEntity<ApiResponse<Project>> delete(@PathVariable String id) 
    {
        Project deletedEntity = service.delete(id);
        ApiResponse<Project> response;
        if(deletedEntity != null) response = new ApiResponse<>(true, "Project entity deleted successfully", deletedEntity);
        else response = new ApiResponse<>(false, "Project entity create failed", deletedEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/project/{id}")
    public ResponseEntity<ApiResponse<Project>> update(@PathVariable String id, @RequestBody Project model) {
        Project updatedEntity = service.update(id, model);
        ApiResponse<Project> response;
        if(updatedEntity != null) response = new ApiResponse<>(true, "Project entity udapted successfully", updatedEntity);
        else response = new ApiResponse<>(false, "Project entity udapted failed", updatedEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
