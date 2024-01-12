package com.lvho.invoice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users", uniqueConstraints={@UniqueConstraint(columnNames={"user_name"}),@UniqueConstraint(columnNames={"email"})})
public class UserInfo { 
  
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id; 

    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email; 

    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    public void setRoles(List<String> values){
        List<String> roleNames = values;
        List<Role> roles = roleNames.stream()
                           .map(Role::valueOf) // Assuming Role is an enum
                           .collect(Collectors.toList());

        this.roles = new ArrayList<>(roles);
    }

    // @ManyToMany
    // @JoinTable(name = "user_role",
    //     joinColumns = @JoinColumn(name = "user_id"),
    //     inverseJoinColumns = @JoinColumn(name = "role_id"))
    // private List<Role> roles = new ArrayList<>();

    // public void addRole(Role role){
    //     roles.add(role);
    //     role.getUsers().add(this);
    // }

    // public void removeRole(Role role){
    //     roles.remove(role);
    //     role.getUsers().remove(this);
    // }
}