package com.lvho.invoice.data.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PurchaseOrderResponse {

    private String id;

    private String name;

    private String email;

    private String phone;

    private String amount;

    private String startDate;

    private String dueDate;

    private List<ProjectResponse> projects = new ArrayList<>();
}
