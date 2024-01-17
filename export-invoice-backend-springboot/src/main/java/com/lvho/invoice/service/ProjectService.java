package com.lvho.invoice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvho.invoice.custom.exception.BadRequestException;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.repository.EmployeeRepository;
import com.lvho.invoice.repository.ProjectRepository;
import com.lvho.invoice.utils.Constants;

import java.util.List;

@Service
public class ProjectService 
{
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public List<Project> getAll()
    {
        return projectRepo.findAll();
    }
    public Project getById(String id)
    {
        return projectRepo.findById(id).orElse(null);
    }

    public Project create(Project project)
    {
        if(project.getName() == null || project.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(projectRepo.findByName(project.getName()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_PROJECT_NAME_EXIST);
        if(project.getStartDate() == null || project.getStartDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(project.getDueDate() == null || project.getDueDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);

        return projectRepo.save(project);
    }

    public Project delete(String id)
    {
        Project project = getById(id);
        if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);
        project.removeThisInAllEmployee();
        projectRepo.deleteById(id);
        return project;
    }

    public Project update(Project model){
        Project project = getById(model.getId());
        if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);
        
        if(model.getName() == null || model.getName().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_NAME);
        if(!project.getName().equals(model.getName()) && projectRepo.findByName(model.getName()) != null) throw new BadRequestException(Constants.MESSAGE_SAME_PROJECT_NAME_EXIST);
        if(model.getCapexCode() == null || model.getCapexCode().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_CAPEX_CODE);
        if(model.getStartDate() == null || model.getStartDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_START_DATE);
        if(model.getDueDate() == null || model.getDueDate().isBlank()) throw new BadRequestException(Constants.MESSAGE_INVALID_DUE_DATE);

        project.setName(model.getName());
        project.setCapexCode(model.getCapexCode());
        project.setStartDate(model.getStartDate());
        project.setDueDate(model.getDueDate());
        project.setDescription(model.getDescription());
        project.setReference(model.getReference());
        return projectRepo.save(project);
    }


    public Project addEmployees(String projectId, List<String> employeeIds){
        Project project = projectRepo.findById(projectId).orElse(null);
        if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);

        employeeIds.forEach(employeeId ->{
            Employee employee = employeeRepo.findById(employeeId).orElse(null);
            if(employee == null) throw new BadRequestException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST);
            
            project.addEmployee(employee);
            employeeRepo.save(employee);
        });

        return projectRepo.save(project);
    }

    public Project removeEmployees(String projectId, List<String> employeeIds)
    {
        Project project = projectRepo.findById(projectId).orElse(null);
        if(project == null) throw new BadRequestException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST);

        employeeIds.forEach(employeeId ->{
            Employee employee = employeeRepo.findById(employeeId).orElse(null);
            if(employee == null) throw new BadRequestException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST);
            
            project.removeEmployee(employee);
        });

        return projectRepo.save(project);
    }
}
