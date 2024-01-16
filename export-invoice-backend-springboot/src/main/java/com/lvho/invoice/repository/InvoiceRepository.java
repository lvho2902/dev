package com.lvho.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvho.invoice.entity.Employee;
import com.lvho.invoice.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    public Invoice findByNumber(String number);
}
