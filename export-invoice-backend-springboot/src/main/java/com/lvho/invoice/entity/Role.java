package com.lvho.invoice.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{

    ROLE_ADMIN, ROLE_MONITORING;

    public String getAuthority() {
      return name();
    }

    public static boolean isValidRoles(List<String> roles){
      List<String> validRoleNames = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toList());
      for (String role : roles) {
          if (!validRoleNames.contains(role)) {
              return false;
          }
      }

      return true;
    }
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    // private String id;

    // @Column(name = "name")
    // private String name;

    // @ManyToMany(mappedBy = "roles")
    // private List<UserInfo> users = new ArrayList<>();
}
