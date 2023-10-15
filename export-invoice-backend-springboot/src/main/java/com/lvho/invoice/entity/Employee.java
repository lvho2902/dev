package com.lvho.invoice.entity;

import jakarta.persistence.Id;
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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
    public String id;

    @Column()
    @NotBlank
    public String name;

    @Column(name = "email")
    @NotBlank
    public String email;

    @Column()
    public String phone;

    @ManyToMany(fetch = FetchType.EAGER,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "employees")
    @JsonIgnore
    public List<Project> projects = new ArrayList<Project>();

    public void addProject(Project project) {
        projects.add(project);
        project.employees.add(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.employees.remove(this);
    }
}
