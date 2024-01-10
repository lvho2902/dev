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

import com.lvho.invoice.data.request.ProjectEmployeeRequest;
import com.lvho.invoice.data.response.ProjectResponse;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.service.ProjectService;
import com.lvho.invoice.utils.Mapper;

import java.util.List;
@RestController
public class ProjectController
{
    @Autowired
    ProjectService service;

    @Autowired
    private Mapper mapper;

    @GetMapping("/project")
    public List<ProjectResponse> getAll()
    {
        return mapper.convertToProjectResponse(service.getAll());
    }

    @GetMapping("project/{id}")
    public ProjectResponse get(@PathVariable String id)
    {
        return mapper.convertToProjectResponse(service.getById(id));
    }

    @PostMapping("project")
    public ResponseEntity<ProjectResponse> create(@RequestBody Project entity)
    {
        Project project = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToProjectResponse(project));
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> delete(@PathVariable String id) 
    {
        Project deletedProject = service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.convertToProjectResponse(deletedProject));
    }

    @PutMapping("/project")
    public ResponseEntity<ProjectResponse> update(@RequestBody Project project) {
        Project updatedProject = service.update(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToProjectResponse(updatedProject));
    }

    @PostMapping("project/add-employee")
    public ResponseEntity<ProjectResponse> addEmployee(@RequestBody ProjectEmployeeRequest entity) {
        ProjectResponse project = mapper.convertToProjectResponse(service.addEmployees(entity.projectId, entity.employeeIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PostMapping("project/remove-employee")
    public ResponseEntity<ProjectResponse> removeEmployee(@RequestBody ProjectEmployeeRequest entity) {
        ProjectResponse project = mapper.convertToProjectResponse(service.removeEmployees(entity.projectId, entity.employeeIds));
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
