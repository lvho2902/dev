package com.lvho.invoice.auth.entity;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"userName"}),@UniqueConstraint(columnNames={"email"})})
public class UserInfo { 
  
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id; 
    private String userName;
    private String email; 
    private String password; 
    private String roles;
} 