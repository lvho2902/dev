package com.lvho.invoice.data.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProjectResponse {
    private String id;

    private String name;

    private String description;

    private String startDate;

    private String dueDate;

    private String reference;

    private int billable;

    private int rate;

    private String capexCode;

    private List<EmployeeResponse> employees = new ArrayList<>();

    private String purchaseOrder;
}
