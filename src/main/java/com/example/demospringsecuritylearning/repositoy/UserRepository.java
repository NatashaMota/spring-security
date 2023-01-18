package com.example.demospringsecuritylearning.repositoy;

import com.example.demospringsecuritylearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String username);
}
