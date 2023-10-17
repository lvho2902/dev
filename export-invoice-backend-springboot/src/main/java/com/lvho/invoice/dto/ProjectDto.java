package com.lvho.invoice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    public String id;

    public String name;

    public String desciprtion;

    public String startDate;
    
    public String dueDate;

    public String reference;

    public int billable;

    public int rate;

    public String capexCode;

    public List<String> employeeIds = new ArrayList<>();

    public String purchaseOrderId;

    public List<String> employeeNames = new ArrayList<>();
}
