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
@Table(name = "purchase_orders", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "amount")
    private int amount;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "due_date")
    private String dueDate;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<Project> projects = new ArrayList<>();

    public void addProject(Project project){
        projects.add(project);
        project.setPurchaseOrder(this);
    }

    public void removeProject(Project project){
        projects.remove(project);
        project.setPurchaseOrder(null);
    }

    public void removeThisInAllProject(){
        if(projects != null){
            projects.forEach(project -> {
                project.setPurchaseOrder(null);
            });
        }
    }
}
