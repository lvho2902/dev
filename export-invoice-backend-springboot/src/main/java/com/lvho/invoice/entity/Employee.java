package com.lvho.invoice.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Employee", uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class Employee {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column()
    public String employeeId;

    @Column()
    @NotBlank
    public String name;

    @Column(name = "email")
    @NotBlank
    public String email;

    @Column()
    public String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    name = "project_employee", 
    joinColumns = @JoinColumn(name = "employee_id"), 
    inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnore
    public List<Project> projects;
}
