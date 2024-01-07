// package com.lvho.invoice.service;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;

// import com.lvho.invoice.custom.Constants;
// import com.lvho.invoice.custom.exception.CustomParameterConstraintException;
// import com.lvho.invoice.dto.ProjectDto;
// import com.lvho.invoice.entity.Employee;
// import com.lvho.invoice.entity.Project;
// import com.lvho.invoice.entity.PurchaseOrder;
// import com.lvho.invoice.repository.EmployeeRepo;
// import com.lvho.invoice.repository.ProjectRepo;
// import com.lvho.invoice.repository.PurchaseOrderRepo;

// import java.util.ArrayList;
// import java.util.List;

// @Service
// public class ProjectService 
// {
//     @Autowired
//     private ProjectRepo projectRepo;

//     @Autowired
//     private PurchaseOrderRepo purchaseOrderRepo;

//     @Autowired
//     private EmployeeRepo employeeRepo;

//     public List<Project> getAll()
//     {
//         return projectRepo.findAll();
//     }
//     public Project get(String id)
//     {
//         return projectRepo.findById(id).orElse(null);
//     }
//     public ProjectDto getDetail(String id)
//     {
//         Project project = projectRepo.findById(id).orElse(null);
//         if(project != null) 
//         {
//             ProjectDto dto = new ProjectDto();
//             dto.id = project.id;
//             dto.name = project.name;
//             dto.startDate = project.startDate;
//             dto.dueDate = project.dueDate;
//             dto.rate = project.rate;
//             dto.desciprtion = project.description;
//             dto.capexCode = project.capexCode;
//             dto.billable = project.billable;
//             dto.purchaseOrderId = project.purchaseOrder.id;
//             if(project.employees != null)
//             project.employees.forEach(employee -> {
//                 dto.employeeNames.add(employee.name);
//                 dto.employeeIds.add(employee.id);
//             });
//             return dto;
//         }
//         return null;
//     }

//     public Project create(ProjectDto dto)
//     {

//         if(dto.name == null || dto.name.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_NAME, HttpStatus.BAD_REQUEST);
//         if(dto.startDate == null || dto.startDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_START_DATE, HttpStatus.BAD_REQUEST);
//         if(dto.dueDate == null || dto.dueDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_DUE_DATE, HttpStatus.BAD_REQUEST);
//         if(projectRepo.findByName(dto.name) != null) throw new CustomParameterConstraintException(Constants.MESSAGE_SAME_PROJECT_NAME_EXIST, HttpStatus.BAD_REQUEST);
//         PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(dto.purchaseOrderId).orElse(null);
//         if(purchaseOrder == null) throw new CustomParameterConstraintException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);

//         Project model = new Project();
//         model.name = dto.name;
//         model.description = dto.desciprtion;
//         model.billable = dto.billable;
//         model.startDate = dto.startDate;
//         model.dueDate = dto.dueDate;
//         model.capexCode = dto.capexCode;
//         model.rate = dto.rate;
//         model.reference = dto.reference;
//         dto.employeeIds.forEach(employeeId ->
//         {
//             Employee employee = employeeRepo.findById(employeeId).orElse(null);
//             if(employee == null) throw new CustomParameterConstraintException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
//             model.addEmployee(employee);
//         });
//         model.purchaseOrder = purchaseOrder;
//         return projectRepo.save(model);
//     }

//     public Project delete(String id)
//     {
//         Project model = get(id);
//         if(model == null) throw new CustomParameterConstraintException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
//         projectRepo.delete(model);
//         return model;
//     }

//     public Project update(ProjectDto dto) 
//     {
//         Project project = get(dto.id);
//         if(project == null) throw new CustomParameterConstraintException(Constants.MESSAGE_PROJECT_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
//         if(dto.name == null || dto.name.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_NAME, HttpStatus.BAD_REQUEST);
//         if(dto.startDate == null || dto.startDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_START_DATE, HttpStatus.BAD_REQUEST);
//         if(dto.dueDate == null || dto.dueDate.isBlank()) throw new CustomParameterConstraintException(Constants.MESSAGE_INVALID_DUE_DATE, HttpStatus.BAD_REQUEST);
//         if(!(project.name.equals(dto.name)) && (projectRepo.findByName(dto.name) != null)) throw new CustomParameterConstraintException(Constants.MESSAGE_SAME_PROJECT_NAME_EXIST, HttpStatus.BAD_REQUEST);
//         PurchaseOrder purchaseOrder = purchaseOrderRepo.findById(dto.purchaseOrderId).orElse(null);
//         if(purchaseOrder == null) throw new CustomParameterConstraintException(Constants.MESSAGE_PURCHASE_ORDER_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);

//         project.name = dto.name;
//         project.description = dto.desciprtion;
//         project.startDate = dto.startDate;
//         project.reference = dto.reference;
//         project.billable = dto.billable;
//         project.rate = dto.rate;
//         project.purchaseOrder = purchaseOrderRepo.findById(dto.purchaseOrderId).orElse(null);
//         if(dto.employeeIds != null)
//         {
//             project.employees = new ArrayList<>();
//             dto.employeeIds.forEach(employeeId ->
//             {
//                 Employee employee = employeeRepo.findById(employeeId).orElse(null);
//                 if(employee == null) throw new CustomParameterConstraintException(Constants.MESSAGE_EMPLOYEE_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
//                 project.addEmployee(employee);
//             });
//         }
//         return projectRepo.save(project);
//     }
// }
