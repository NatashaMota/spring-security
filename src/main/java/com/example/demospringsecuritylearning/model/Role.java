package com.example.demospringsecuritylearning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


import java.util.UUID;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Role implements GrantedAuthority {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
   private String role;

   @OneToMany
   private Set<User> users;

   @Override
   public String getAuthority() {
      return "ROLE_"+this.role;
   }
}
