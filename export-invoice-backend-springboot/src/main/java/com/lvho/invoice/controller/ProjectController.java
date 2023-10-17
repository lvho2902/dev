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
        List<Project> aa = service.getAll();
        return aa;
    }

    @GetMapping("project/{id}")
    public ProjectDto get(@PathVariable String id)
    {
        return service.getDetail(id);
    }

    @PostMapping("project")
    public ResponseEntity<ApiResponse<Project>> create(@RequestBody ProjectDto dto)
    {
        Project createdEntity = service.create(dto);
        ApiResponse<Project> response = new ApiResponse<Project>("Project entity created successfully", createdEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }    

    @DeleteMapping("/project/{id}")
    public ResponseEntity<ApiResponse<Project>> delete(@PathVariable String id) 
    {
        Project deletedEntity = service.delete(id);
        ApiResponse<Project> response = new ApiResponse<Project>("Project entity deleted successfully", deletedEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/project")
    public ResponseEntity<ApiResponse<Project>> update(@RequestBody ProjectDto dto) {
        Project updatedEntity = service.update(dto);
        ApiResponse<Project> response = new ApiResponse<Project>("Project entity udapted successfully", updatedEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
