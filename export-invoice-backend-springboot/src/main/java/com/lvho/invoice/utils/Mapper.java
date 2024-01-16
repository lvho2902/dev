package com.lvho.invoice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lvho.invoice.data.response.EmployeeResponse;
import com.lvho.invoice.data.response.InvoiceResponse;
import com.lvho.invoice.data.response.ProjectResponse;
import com.lvho.invoice.data.response.CustomerResponse;
import com.lvho.invoice.data.response.UserInfoResponse;
import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.entity.Invoice;
import com.lvho.invoice.entity.Project;
import com.lvho.invoice.entity.Customer;
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
        // if(project.getInvoice() != null) projectResponse.setInvoice((project.getInvoice().getNumber()));
        // else projectResponse.setInvoice(null);
        return projectResponse;
    }

    public List<ProjectResponse> convertToProjectResponse(List<Project> projects){        
        return projects.stream().map(project -> {
            return convertToProjectResponse(project);
        }).collect(Collectors.toList());
    }

    public CustomerResponse convertToCustomerResponse(Customer customer){
        CustomerResponse customerResponse = mapper.map(customer, CustomerResponse.class);

        // List<InvoiceResponse> invoiceResponses = customer.getInvoices().stream().map(invoice ->{
        //     return convertToProjectResponse(invoice);
        // }).collect(Collectors.toList());

        // customerResponse.setProjects(projectResponses);
        return customerResponse;
    }

    public List<CustomerResponse> convertToCustomerResponse(List<Customer> customers){
        return customers.stream().map(customer ->{
            return convertToCustomerResponse(customer);
        }).collect(Collectors.toList());
    }

    public InvoiceResponse convertToInvoiceResponse(Invoice invoice){
        InvoiceResponse invoiceResponse = mapper.map(invoice, InvoiceResponse.class);
        // if(invoice.getCustomer() != null) invoiceResponse.setCustomer(invoice.getCustomer().getName());
        // else invoiceResponse.setCustomer(null);
        return invoiceResponse; 
    }

    public List<InvoiceResponse> convertToInvoiceResponse(List<Invoice> invoices){
        return invoices.stream().map(invoice -> {
            return convertToInvoiceResponse(invoice);
        }).collect(Collectors.toList());
    }
}