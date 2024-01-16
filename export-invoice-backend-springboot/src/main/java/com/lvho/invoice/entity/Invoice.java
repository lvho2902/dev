package com.lvho.invoice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invoices", uniqueConstraints={@UniqueConstraint(columnNames={"number"})})
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String number;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "total")
    private int total;

    @ManyToOne
    private Customer customer;

//     @OneToMany(mappedBy = "invoice")
//     private List<Project> projects = new ArrayList<>();

//     public void addProject(Project project){
//         projects.add(project);
//         project.setInvoice(this);
//     }

//     public void removeProject(Project project){
//         projects.remove(project);
//         project.setInvoice(null);
//     }

//     public void removeThisInAllProject(){
//         if(projects != null){
//             projects.forEach(project -> {
//                 project.setInvoice(null);
//             });
//         }
//     }
}
