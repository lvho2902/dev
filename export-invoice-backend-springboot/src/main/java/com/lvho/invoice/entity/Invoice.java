package com.lvho.invoice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    @Column(name = "number")
    private String number;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "total")
    private int total;
    
    @Column(name = "remaining")
    private int remaining;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "invoice")
    private List<Project> projects = new ArrayList<>();

    public void setCustomer(Customer customer){
        this.customer = customer;
        calcTotal();
        calcRemaining();
    }

    public void addProject(Project project){
        if(projects.contains(project)) return;
        projects.add(project);
        project.setInvoice(this);

        calcTotal();
        calcRemaining();
    }

    public void removeProject(Project project){
        if(!projects.contains(project)) return;
        projects.remove(project);
        project.setInvoice(null);

        calcTotal();
        calcRemaining();
    }

    public void removeThisInAllProject(){
        if(projects != null){
            projects.forEach(project -> {
                project.setInvoice(null);
            });
        }
    }

    public void generateRandomInvoiceNumber(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder invoiceNumber = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            invoiceNumber.append(randomChar);
        }

        this.number =  "TMA-" + invoiceNumber.toString();
    }

    public void calcTotal(){
        total = 0;
        if(projects == null || projects.isEmpty()) return;

        projects.forEach(project -> {
            total += project.getBillable()*project.getRate();
        });

    }

    public void calcRemaining(){
        remaining = 0;
        if(customer == null) return;

        remaining = customer.getAmount() - total;
    }
}
