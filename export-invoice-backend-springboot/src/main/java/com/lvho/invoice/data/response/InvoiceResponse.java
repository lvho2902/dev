package com.lvho.invoice.data.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class InvoiceResponse {

    private String id;

    private String number;

    private String startDate;

    private String dueDate;

    private int total;

    private int remaining;

    private CustomerResponse customer;

    private List<ProjectResponse> projects;
}
