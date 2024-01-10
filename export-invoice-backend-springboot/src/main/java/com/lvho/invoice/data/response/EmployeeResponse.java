package com.lvho.invoice.data.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmployeeResponse {

    private String id;

    private String name;

    private String email;

    private String phone;

    private List<String> projects = new ArrayList<>();
}
