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
@Table(name = "PurchaseOrder", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column()
    public String id;

    @Column()
    @NotBlank
    public String name;

    @Column()
    @NotBlank
    public String email;

    @Column()
    public String phone;

    @Column()
    @NotBlank
    public String amount;

    @Column()
    @NotBlank
    public String startDate;

    @Column()
    @NotBlank
    public String dueDate;
}
