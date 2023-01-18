package com.example.demospringsecuritylearning.data;

import com.example.demospringsecuritylearning.model.Role;
import com.example.demospringsecuritylearning.model.User;
import com.example.demospringsecuritylearning.model.UserRole;
import com.example.demospringsecuritylearning.repositoy.RoleRepository;
import com.example.demospringsecuritylearning.repositoy.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Bootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.loadData();
    }

    public void loadData(){

        System.out.println("here");

        Role admRole = new Role();
        admRole.setRole(UserRole.ADMIN.name());
        roleRepository.save(admRole);

        Role usrRole = new Role();
        usrRole.setRole(UserRole.USER.name());
        roleRepository.save(usrRole);

        User usr = new User("adm", passwordEncoder.encode("senha"), admRole, true, true, true, true);
        User adm = new User("usr", passwordEncoder.encode("senha"), usrRole, true, true, true, true );

        userRepository.save(usr);
        userRepository.save(adm);
    }
}
