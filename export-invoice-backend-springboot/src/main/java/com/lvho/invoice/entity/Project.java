// package com.lvho.invoice.entity;

// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
// import jakarta.validation.constraints.NotBlank;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;

// import java.util.ArrayList;
// import java.util.List;

// import org.hibernate.annotations.OnDelete;
// import org.hibernate.annotations.OnDeleteAction;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;

// @Entity
// @NoArgsConstructor
// @AllArgsConstructor
// @Getter
// @Setter
// @Table(name = "Project", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
// public class Project {
//     @Id
//     @GeneratedValue(strategy =  GenerationType.UUID)
//     @Column()
//     public String id;

//     @Column()
//     @NotBlank
//     public String name;

//     @Column()
//     public String description;

//     @Column()
//     @NotBlank
//     public String startDate;

//     @Column()
//     @NotBlank
//     public String dueDate;

//     @Column()
//     public String reference;

//     @Column()
//     public int billable;

//     @Column()
//     public int rate;

//     @Column()
//     public String capexCode;

//     @ManyToMany(fetch = FetchType.EAGER,
//         cascade = {
//             CascadeType.PERSIST,
//             CascadeType.MERGE
//       })
//     @JoinTable(name = "project_employees",
//         joinColumns = { @JoinColumn(name = "project_id") },
//         inverseJoinColumns = { @JoinColumn(name = "employee_id") })
//     public List<Employee> employees = new ArrayList<Employee>();

//     @ManyToOne(fetch = FetchType.EAGER, optional = true)
//     @JoinColumn(name = "purchase_order_id", nullable = false)
//     @OnDelete(action = OnDeleteAction.CASCADE)
//     public PurchaseOrder purchaseOrder;
// }
