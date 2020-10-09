package com.whoiscan.userregistertest.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.whoiscan.userregistertest.entity.User;
import com.whoiscan.userregistertest.repository.RoleRepository;
import com.whoiscan.userregistertest.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.datasource.initialization-mode}")
    String initialMode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            userRepository.save(new User("superAdmin","superadmin@gmail.com", passwordEncoder.encode("1930"),
                    "Adminstrator",roleRepository.findAll()));
        }
    }
}
