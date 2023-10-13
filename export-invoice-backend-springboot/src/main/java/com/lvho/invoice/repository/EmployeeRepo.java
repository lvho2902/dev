package com.lvho.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvho.invoice.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String>
{
}
