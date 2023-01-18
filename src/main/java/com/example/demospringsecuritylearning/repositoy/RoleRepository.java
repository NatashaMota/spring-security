package com.example.demospringsecuritylearning.repositoy;

import com.example.demospringsecuritylearning.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByRole(String role);
}
