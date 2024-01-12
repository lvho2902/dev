package com.lvho.invoice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lvho.invoice.data.response.EmployeeResponse;
import com.lvho.invoice.data.response.ProjectResponse;
import com.lvho.invoice.data.response.PurchaseOrderResponse;
import com.lvho.invoice.data.response.UserInfoResponse;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.entity.PurchaseOrder;
import com.lvho.invoice.entity.UserInfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class Mapper {
    @Autowired
    private ModelMapper mapper;

    public UserDetails convertToUserDetails(UserInfo user){
        return mapper.map(user, UserDetails.class);
    }

    public UserInfoResponse convertToUserInfoResponse(UserInfo user){
        return mapper.map(user, UserInfoResponse.class);
    }

    public EmployeeResponse convertToEmployeeResponse(Employee employee){
        if(employee == null) return null;
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
        List<String> projectNames = employee.getProjects().stream().map(Project::getName).collect(Collectors.toList());
        employeeResponse.setProjects(projectNames);
        return employeeResponse;
    }

    public List<EmployeeResponse> convertToEmployeeResponse(List<Employee> employees){
        return employees.stream().map(employee -> {
            return convertToEmployeeResponse(employee);
            }).collect(Collectors.toList());
    }

    public ProjectResponse convertToProjectResponse(Project project){
        ProjectResponse projectResponse = mapper.map(project, ProjectResponse.class);
        
        List<EmployeeResponse> employeeResponses = project.getEmployees().stream().map(employee -> {
            return convertToEmployeeResponse(employee);
        }).collect(Collectors.toList());

        projectResponse.setEmployees(employeeResponses);
        if(project.getPurchaseOrder() != null) projectResponse.setPurchaseOrder(project.getPurchaseOrder().getName());
        else projectResponse.setPurchaseOrder(null);
        return projectResponse;
    }

    public List<ProjectResponse> convertToProjectResponse(List<Project> projects){        
        return projects.stream().map(project -> {
            return convertToProjectResponse(project);
        }).collect(Collectors.toList());
    }

    public PurchaseOrderResponse convertToPurchaseOrderResponse(PurchaseOrder purchaseOrder){
        PurchaseOrderResponse purchaseOrderResponse = mapper.map(purchaseOrder, PurchaseOrderResponse.class);

        List<ProjectResponse> projectResponses = purchaseOrder.getProjects().stream().map(project ->{
            return convertToProjectResponse(project);
        }).collect(Collectors.toList());

        purchaseOrderResponse.setProjects(projectResponses);
        return purchaseOrderResponse;
    }

    public List<PurchaseOrderResponse> convertToPurchaseOrderResponse(List<PurchaseOrder> purchaseOrders){
        return purchaseOrders.stream().map(purchaseOrder ->{
            return convertToPurchaseOrderResponse(purchaseOrder);
        }).collect(Collectors.toList());
    }
}