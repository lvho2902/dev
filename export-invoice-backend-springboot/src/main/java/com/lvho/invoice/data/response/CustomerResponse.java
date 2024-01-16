package com.lvho.invoice.data.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomerResponse {

    private String id;

    private String name;

    private String email;

    private String phone;

    private String amount;

    private String address;

    // private List<InvoiceResponse> invoices = new ArrayList<>();
}
