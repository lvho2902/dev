package com.lvho.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvho.invoice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>
{
    public Customer findByName(String name);
    public Customer findByEmail(String email);
    public Customer findByPhone(String phone);
}
