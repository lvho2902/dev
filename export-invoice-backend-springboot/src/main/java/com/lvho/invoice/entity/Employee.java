package com.lvho.invoice.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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

    @NotBlank
    public String email;

    @Column()
    public String phone;

//     @ManyToMany(fetch = FetchType.EAGER,
//         cascade = {
//             CascadeType.PERSIST,
//             CascadeType.MERGE
//         },
//         mappedBy = "employees")
//     @JsonIgnore
//     public List<Project> projects = new ArrayList<Project>();
}
