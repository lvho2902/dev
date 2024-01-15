package com.lvho.invoice.entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "customers", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Customer {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "amount")
    private int amount;

    @OneToMany(mappedBy = "customer")
    private List<Project> projects = new ArrayList<>();

    public void addProject(Project project){
        projects.add(project);
        project.setCustomer(this);
    }

    public void removeProject(Project project){
        projects.remove(project);
        project.setCustomer(null);
    }

    public void removeThisInAllProject(){
        if(projects != null){
            projects.forEach(project -> {
                project.setCustomer(null);
            });
        }
    }
}
