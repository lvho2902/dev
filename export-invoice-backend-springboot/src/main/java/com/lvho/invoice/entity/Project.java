package com.lvho.invoice.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "projects", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Project {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "due_data")
    @NotBlank
    private String dueDate;

    @Column(name = "reference")
    private String reference;

    @Column(name = "billable")
    private int billable;

    @Column(name = "rate")
    private int rate;

    @Column(name = "capex_code")
    private String capexCode;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "project_employee",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();

    @ManyToOne
    private Invoice invoice;

    public void addEmployee(Employee employee){
        if(employees.contains(employee)) return;
        employees.add(employee);
        employee.getProjects().add(this);
        
        calcBillable();
    }

    public void removeEmployee(Employee employee){
        if(employees == null) return;
        employees.remove(employee);
        employee.getProjects().remove(this);

        calcBillable();
    }

    public void removeThisInAllEmployee(){
        if(employees == null) return;
        employees.forEach(employee -> {
            employee.getProjects().remove(this);
        });
    }

    private void calcBillable(){
        billable = 0;
        if(employees == null || employees.isEmpty()) return;
        billable = employees.size();

        if(invoice != null){
            invoice.calcTotal();
            invoice.calcRemaining();
        }
    }
}
